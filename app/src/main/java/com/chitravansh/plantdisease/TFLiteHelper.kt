package com.chitravansh.plantdisease

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class TFLiteHelper(
    private val assetManager: AssetManager,
    private val modelPath: String = "plant_disease_model.tflite",
    private val labelsPath: String = "label.txt"
) {

    private val interpreter: Interpreter
    private val labels: List<String>

    private val inputWidth: Int
    private val inputHeight: Int
    private val inputChannels: Int
    private val numClasses: Int

    init {
        try {
            // 1) Load model
            interpreter = Interpreter(loadModelFile(modelPath))

            // 2) Read input tensor info
            val inputTensor = interpreter.getInputTensor(0)
            val inShape = inputTensor.shape()   // [1, h, w, c]
            val inType = inputTensor.dataType()

            require(inType == DataType.FLOAT32) {
                "This helper expects a FLOAT32 model input. Current input type: $inType"
            }

            inputHeight = inShape[1]
            inputWidth = inShape[2]
            inputChannels = inShape[3]

            // 3) Read output tensor info
            val outputTensor = interpreter.getOutputTensor(0)
            val outShape = outputTensor.shape() // [1, numClasses]
            val outType = outputTensor.dataType()

            require(outType == DataType.FLOAT32) {
                "This helper expects a FLOAT32 model output. Current output type: $outType"
            }

            numClasses = outShape[1]

            // 4) Load labels
            labels = loadLabels(labelsPath)

            require(labels.size == numClasses) {
                "Label count (${labels.size}) does not match model output classes ($numClasses)"
            }

            Log.d(
                "TFLiteHelper",
                "Model loaded. Input=${inputWidth}x$inputHeight x $inputChannels FLOAT32, " +
                        "Output=$numClasses classes FLOAT32, Labels=${labels.joinToString()}"
            )

        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Failed to init TFLiteHelper: ${e.message}")
        }
    }

    private fun loadModelFile(path: String): MappedByteBuffer {
        val fd = assetManager.openFd(path)
        FileInputStream(fd.fileDescriptor).use { fis ->
            val channel = fis.channel
            return channel.map(
                FileChannel.MapMode.READ_ONLY,
                fd.startOffset,
                fd.declaredLength
            )
        }
    }

    private fun loadLabels(path: String): List<String> {
        val list = mutableListOf<String>()
        assetManager.open(path).use { input ->
            InputStreamReader(input).useLines { lines ->
                lines.forEach { line ->
                    val t = line.trim()
                    if (t.isNotEmpty()) list.add(t)
                }
            }
        }
        if (list.isEmpty()) {
            throw IllegalStateException("Label file '$path' is empty or missing")
        }
        return list
    }

    fun classify(bitmap: Bitmap): Pair<String, Float> {
        // 1) Resize input
        val scaled = Bitmap.createScaledBitmap(bitmap, inputWidth, inputHeight, true)

        // 2) Create float32 input buffer [0..1]
        val inputBuffer = ByteBuffer
            .allocateDirect(4 * inputWidth * inputHeight * inputChannels)
            .order(ByteOrder.nativeOrder())

        val pixels = IntArray(inputWidth * inputHeight)
        scaled.getPixels(pixels, 0, inputWidth, 0, 0, inputWidth, inputHeight)

        var idx = 0
        for (y in 0 until inputHeight) {
            for (x in 0 until inputWidth) {
                val p = pixels[idx++]
                val r = (p shr 16) and 0xFF
                val g = (p shr 8) and 0xFF
                val b = p and 0xFF

                inputBuffer.putFloat(r / 255f)
                inputBuffer.putFloat(g / 255f)
                inputBuffer.putFloat(b / 255f)
            }
        }
        inputBuffer.rewind()

        // 3) Run inference – output is already probabilities (softmax) in FLOAT32
        val output = Array(1) { FloatArray(numClasses) }
        interpreter.run(inputBuffer, output)

        val probs = output[0]

        // 4) Argmax
        var bestIdx = 0
        var bestScore = probs[0]
        for (i in 1 until probs.size) {
            if (probs[i] > bestScore) {
                bestScore = probs[i]
                bestIdx = i
            }
        }

        val bestLabel = if (bestIdx in labels.indices) labels[bestIdx] else "unknown"

        Log.d(
            "TFLiteHelper",
            "Probs = ${probs.map { "%.3f".format(it) }} => $bestLabel (${bestScore})"
        )

        return bestLabel to bestScore
    }

    fun close() = interpreter.close()
}
