# Shilpa-Kala 🎨
### Empowering Indian Artisans with Professional Digital Branding

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5+-green.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Hilt](https://img.shields.io/badge/Hilt-Dependency%20Injection-orange.svg?style=flat)](https://developer.android.com/training/dependency-injection/hilt-android)
[![MVVM](https://img.shields.io/badge/Architecture-MVVM-lightgrey.svg?style=flat)]()

**Shilpa-Kala** (Sanskrit for *"Art of Craft"*) is a purpose-built Android application designed to bridge the digital divide for Indian artisans. By providing professional-grade branding tools in a simple mobile interface, the app helps traditional craftsmen elevate the perceived value of their heritage products.

---

## 😟 Problem Statement
Artisans across Karnataka—including legendary Channapatna wood carvers and Gombe toy makers—often struggle to market their products online. Poor lighting, cluttered backgrounds, and lack of branding in photos lead to a **3-5x lower perceived value** compared to commercially branded items. Shilpa-Kala solves this by putting a professional studio and branding engine right in the artisan's pocket.

---

## ✨ Features
*   **Artisan Profile Setup:** Simple onboarding to capture name, village location, and craft type.
*   **Guided Camera:** Product frame overlays to ensure perfect composition every time.
*   **Gallery Import:** Support for enhancing existing photos from the device gallery.
*   **Studio Backgrounds:** 5 preset professional backgrounds:
    *   ⚪ White (Clean/E-commerce)
    *   🌾 Jute (Natural/Organic)
    *   🪵 Dark Wood (Premium/Antique)
    *   🏛️ Marble (Elegant/Modern)
    *   🏺 Terracotta (Traditional/Earthly)
*   **Heritage Branding Engine:** Automatic "Handmade in Karnataka" label to signify authenticity.
*   **Professional Overlays:** Smart artisan name stamps and price tag overlays for instant trust.
*   **Branded Gallery:** A dedicated in-app space to manage all professionally branded photos.
*   **One-Tap Share:** Instant sharing to WhatsApp, Instagram, and Facebook to reach global customers.

---

## 🛠 Tech Stack
*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose
*   **Camera API:** CameraX
*   **Database:** Room DB (Local storage for profiles and gallery data)
*   **DI:** Hilt (Dagger Hilt)
*   **Architecture:** MVVM (Model-View-ViewModel)
*   **Navigation:** Navigation Compose
*   **Image Loading:** Coil

---

## 🏗 Architecture
The project follows **Clean Architecture** principles with **MVVM** and the **Repository Pattern**.

```text
       ┌──────────┐      ┌──────────┐      ┌──────────────┐
       │    UI    │ ────▶│ ViewModel│ ────▶│  Repository  │
       │ (Compose)│      │          │      │              │
       └──────────┘      └──────────┘      └──────────────┘
                                                  │
                                         ┌────────┴────────┐
                                         ▼                 ▼
                                  ┌────────────┐    ┌────────────┐
                                  │   Local    │    │   Camera   │
                                  │ (Room DB)  │    │ (CameraX)  │
                                  └────────────┘    └────────────┘
```

---

## 📂 Project Structure
```text
com.shilpakala
├── data          # Data source implementations (Room, DAOs, Repositories)
├── di            # Dependency Injection modules (Hilt)
├── domain        # Business logic models and repository interfaces
├── ui            # Composable screens, ViewModels, and Navigation
│   ├── theme     # Compose Material3 theme definitions
│   └── components # Reusable UI widgets
└── utils         # Helper classes for image processing and file handling
```

---

## 🚀 How to Run
1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/Shilpa-Kala.git
    ```
2.  **Open in Android Studio:**
    File > Open > Select the `Shilpa-Kala` folder.
3.  **Sync Gradle:**
    Wait for Android Studio to download dependencies and sync.
4.  **Run:**
    Select your device/emulator and click the **Run** button (Shift + F10).

---

## 🧪 Testing
*   **Unit Tests:** Business logic testing in the domain and data layers.
*   **Instrumented Tests:** Database and Preference testing on-device.
*   **UI Tests:** Compose UI tests using the standard testing library.

---

## 🤖 Built With AI
This project is a testament to the power of **AI-assisted development**:
*   **Architected by:** Lohith G (Architecture and System Design).
*   **Implementation Agents:** Built using **Cursor AI** and **Windsurf AI** agents.
*   **Refinement:** Android Studio AI chat was utilized for troubleshooting and UI improvements.
*   **Workflow:** **100% AI-assisted development**—from zero to ship without manual code writing.
*   **Approach:** Demonstrates the modern **AI Engineer** role: *Architect, Prompt, Review, and Ship.*

---

## 🎓 Internship Context
*   **Program:** MindMatrix VTU Internship Program 2025
*   **Domain:** Android App Development using GenAI
*   **Batch:** Originators-G3

---
*Created with ❤️ for the artisans of India.*
