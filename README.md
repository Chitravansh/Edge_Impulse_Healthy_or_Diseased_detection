# 🌿 LeafGuard — Edge AI Plant Leaf Health Classifier (Android | Offline TFLite)

LeafGuard is a real‑time, offline, on‑device Android prototype that classifies plant leaf health as **Healthy** or **Diseased**, using a compact TensorFlow Lite model exported from Edge Impulse Studio. Inference runs entirely on the phone itself — serving as an **edge device** — via live camera input powered by CameraX.

---

## 🚜 Problem

Farmers often lack fast AI tools that can diagnose leaf issues **without internet**, causing delayed detection and limited field usability.

**Goal:** Instant, offline, camera‑driven ML classification on Android.

---

## ✅ MVP Features

✔ 2‑class classifier (`Healthy` / `Diseased`)
✔ Offline inference (no cloud dependency)
✔ Real‑time camera input using CameraX
✔ Lightweight TFLite model deployed from Edge Impulse
✔ Prediction + confidence display
✔ Android phone/emulator used as edge hardware

---

## 📁 Model & Labels

Add required assets to:

```
app/src/main/assets/
├── plant_disease_model.tflite
└── label.txt
```

Contents of `label.txt` (must match model output index order):

```
healthy
diseased
```

---

## 🧠 Edge AI Workflow Summary

1. Prototype dataset created and model trained in Edge Impulse Studio
2. Model exported as `plant_disease_model.tflite`
3. Camera frames (`ImageProxy`) converted → `Bitmap` → model input
4. Model + labels loaded from `assets/` for on‑device classification
5. Softmax probabilities mapped to labels for prediction

---

## ⚙️ Tech Stack

| Component          | Technology               |
| ------------------ | ------------------------ |
| Language           | Kotlin                   |
| UI                 | Jetpack Compose          |
| Camera             | CameraX                  |
| ML Runtime         | TensorFlow Lite          |
| ML Studio & Export | Edge Impulse             |
| Edge Device        | Android Phone / Emulator |
| Code Hosting       | GitHub                   |

---

## 📱 Testing on Android Phone (Edge Device)

1. Open project in Android Studio
2. Connect phone via USB
3. Enable in phone:

    * Developer Options → USB Debugging ON
    * USB Mode → File Transfer / PTP / MTP
4. Run ▶ app from Android Studio
5. Allow Camera permission
6. Capture a real leaf → view prediction + confidence

---

## 📤 Exported APK Sample Included

A built installable sample APK is included in the repository.

```
apk_release/
├── LeafGuard.apk   ← Installable demo sample of the Android app
```

> 📌 This APK file can be found inside the `apk_release/apk_release` folder in the project, generated via **Build → Build APK(s) → Locate in outputs/apk/release/**.

---

## 🔍 Sample Result

```
Prediction: Healthy
Confidence: 73.0 %
```

---

## ⚠ Limitations

* 2‑class prototype only
* Not crop‑wise or disease‑specific yet
* Model kept lightweight for quick edge inference demo

---

## 🔮 Future Scope (Next Version)

* Multi‑crop classifications
* Specific agriculture disease labels
* Larger custom field datasets
* AI‑driven treatment suggestions
* Low‑power optimizations

---

## ⭐ Why this qualifies as Edge AI

* ML model runs **on device itself** (Android = edge hardware)
* No server/cloud at runtime
* Works fully **offline with low latency**
* Camera is the real‑time ML input sensor
* Model pipeline built using Edge Impulse → optimized to TFLite

---

## 👨‍💻 Author

**Chitravansh**
Participant — HackerEarth × Edge Impulse Edge AI Contest

---

## 🏷️ Tags

`Edge AI` · `On‑Device ML` · `Offline Inference` · `Android Kotlin` · `TFLite` · `CameraX` · `Agriculture AI Prototype` · `Hackathon MVP

---

⭐ If this helped, feel free to star ⭐ this GitHub repo to support AI‑at‑edge innovation!
