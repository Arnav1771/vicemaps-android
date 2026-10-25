# 🌴 ViceMaps 3D · Native Android Application

> 100% Pure Native Android implementation of the **GTA VI Dusk 3D Map Engine**, engineered in **Kotlin 2.0** and **Jetpack Compose** (Material 3 Dark).

Unlike hybrid webview wrappers (e.g., Capacitor in CS Arcade), ViceMaps Android is built completely natively with direct hardware acceleration, real-time GPS location providers, and native 3D vector tile rendering.

---

## ⚡ Native Android Architecture

- **Language**: Kotlin 2.0 (Target SDK 35, Min SDK 26)
- **UI Framework**: Jetpack Compose + Material 3 Dark
- **Map Engine**: **MapLibre Native Android SDK** (`org.maplibre.gl:android-sdk:11.5.0`)
- **3D Hardware Acceleration**: OpenGL ES 3.0 / Vulkan rendering pipeline
- **Hardware GPS**: `com.google.android.gms.location.FusedLocationProviderClient`
- **Worldwide Routing**: Native OkHttp + Kotlin Coroutines with OSRM global routing API

---

## 🏗️ Project Structure

```
vicemaps-android/
├── app/
│   ├── build.gradle.kts                   # Dependencies & Android SDK 35 config
│   ├── src/main/
│   │   ├── AndroidManifest.xml            # Hardware GPS & Network permissions
│   │   └── java/com/vicemaps/app/
│   │       ├── MainActivity.kt            # Jetpack Compose Entrypoint
│   │       ├── ui/
│   │       │   ├── theme/Color.kt         # GTA VI Neon Dusk Palette
│   │       │   ├── theme/Theme.kt         # Material3 Dark Theme
│   │       │   └── components/
│   │       │       ├── TopHudBar.kt       # Search & Locate Me FAB
│   │       │       └── RouteHudCard.kt    # Turn-by-Turn GPS Waypoint Card
│   │       ├── map/
│   │       │   └── ViceMapEngine.kt       # MapLibre Native & 3D Extrusions
│   │       ├── routing/
│   │       │   └── OsrmClient.kt          # Native OSRM HTTP Router
│   │       └── location/
│   │           └── DeviceLocationManager.kt# Hardware Fused Location Flow
└── gradle/
    └── libs.versions.toml                 # Version catalog
```

---

## 🛠️ Building & Running

1. Open project in **Android Studio Ladybug (2024.2+)** or newer.
2. Ensure Android SDK 35 and JDK 17 are installed.
3. Sync Gradle and Run on an Android Device or Emulator.

```bash
./gradlew assembleDebug
```

---

## 📄 License

MIT License © 2026 Arnav Bhargava.
