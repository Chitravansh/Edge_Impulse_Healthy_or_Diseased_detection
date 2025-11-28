package com.chitravansh.plantdisease

import android.graphics.*
import androidx.camera.core.ImageProxy
import java.io.ByteArrayOutputStream

object ImageUtils {

    fun imageProxyToBitmap(image: ImageProxy): Bitmap? {
        // Image format must be YUV_420_888
        val yPlane = image.planes[0]
        val uPlane = image.planes[1]
        val vPlane = image.planes[2]

        val yBuffer = yPlane.buffer
        val uBuffer = uPlane.buffer
        val vBuffer = vPlane.buffer

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        // NV21 layout: Y + VU
        val nv21 = ByteArray(ySize + uSize + vSize)
        yBuffer.get(nv21, 0, ySize)

        // V and U are swapped for NV21
        val chromaStart = ySize
        var i = 0
        while (i < vSize) {
            nv21[chromaStart + i] = vBuffer.get(i)
            nv21[chromaStart + i + 1] = uBuffer.get(i)
            i += 2
        }

        val yuvImage = YuvImage(
            nv21,
            ImageFormat.NV21,
            image.width,
            image.height,
            null
        )

        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, image.width, image.height), 100, out)
        val imageBytes = out.toByteArray()
        var bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            ?: return null

        // Apply camera rotation
        val rotationDegrees = image.imageInfo.rotationDegrees
        if (rotationDegrees != 0) {
            val matrix = Matrix().apply { postRotate(rotationDegrees.toFloat()) }
            bitmap = Bitmap.createBitmap(
                bitmap,
                0, 0, bitmap.width, bitmap.height,
                matrix,
                true
            )
        }

        return bitmap
    }
}
