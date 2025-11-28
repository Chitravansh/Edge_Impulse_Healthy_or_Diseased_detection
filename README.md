# 🌿 LeafGuard — Edge AI Plant Leaf Health Detector (Android | Offline TFLite)

LeafGuard is a real-time, offline, on-device Android prototype that classifies plant leaf health into **Healthy** or **Diseased**, using a compact TensorFlow Lite model exported via Edge Impulse and powered by CameraX for real-time camera capture.

---

## 🚜 Real‑World Problem

Farmers often lack fast AI‑powered tools that can instantly analyze leaf health **without internet**. This leads to:

* Delayed diagnosis
* Limited accessibility in rural areas
* Dependence on cloud solutions

**Goal:** Provide instant, offline, mobile‑based leaf health detection using edge‑inference on Android.

---

## ✅ MVP — What’s Implemented

✔ Offline inference (2‑class image classifier)
✔ Live camera preview + capture using CameraX
✔ TFLite model execution directly on device
✔ Prediction + confidence score display
✔ Works on emulator/phone for quick testing

---

## 🧠 AI/ML Implementation Summary

1. Model trained (prototype 2‑class dataset) using Edge Impulse Studio
2. Model exported as `plant_disease_model.tflite`
3. Labels mapped to model output order in `label.txt`
4. Camera frames converted from `ImageProxy` (YUV) → `Bitmap` → model input
5. App loads `.tflite` + `label.txt` from **assets/** for inference

---

## 📁 Required Assets Included

Place the following under:

```
app/src/main/assets/
├── plant_disease_model.tflite
└── label.txt
```

`label.txt` must match model output order. Example:

```
healthy
diseased
```

---

## ⚙️ Tech Stack

| Component                 | Technology               |
| ------------------------- | ------------------------ |
| Language                  | Kotlin                   |
| UI                        | Jetpack Compose          |
| Camera                    | CameraX                  |
| ML Runtime                | TensorFlow Lite          |
| ML Studio/Export          | Edge Impulse             |
| Edge Inference Device     | Android Phone / Emulator |
| Code Hosting (submission) | GitHub                   |

---

## 📱 How to Test on Android Phone (Edge Device)

1. Open project in Android Studio
2. Connect phone via USB
3. In phone, enable:

    * Developer Options
    * USB Debugging
    * File Transfer (PTP/MTP)
4. Run ▶ from Android Studio
5. Grant **Camera Permission**
6. Capture/Pan a real leaf → view classification result + confidence

---

## 🔍 Sample Output (for reference)

```
Prediction: Healthy
Confidence: 73.0 %
```

---

## ⚠ Current Prototype Limitations

* 2‑class model only (`Healthy` / `Diseased`)
* Not crop‑wise or disease‑specific yet
* Dataset kept small for a **quick edge‑deployment MVP demo**

---

## 🔮 Future Scope (Not part of MVP)

* Multi‑crop and disease‑specific detection
* Larger field datasets
* AI‑based treatment suggestions
* Model optimizations for low‑power edge hardware

---

## ⭐ Why LeafGuard is an Edge AI Solution

* Inference runs **on device** (Android = edge hardware)
* ML model exported from Edge Impulse to TFLite
* No cloud dependency at runtime
* Camera is used as the real‑time sensor input to ML pipeline
* Works fully **offline with minimal latency**

---

## 👨‍💻 Author

**Chitravansh**
Participant — HackerEarth × Edge Impulse Edge AI Contest

---

## 📌 Tags

`Edge AI` · `On‑Device ML` · `Offline Inference` · `Android Kotlin` · `TFLite` · `CameraX` · `Agriculture AI Prototype` · `Hackathon MVP`

---

⭐ If this helped, feel free to star the repo to support AI‑at‑edge innovation!
