# OnStand

A beautiful Compose Multiplatform (KMP) live clock app with animated backgrounds.

<img width="2064" height="2752" alt="image" src="https://github.com/user-attachments/assets/d8edb6bb-e762-49e1-9c66-3a0c76751145" />
<img width="2064" height="2752" alt="image" src="https://github.com/user-attachments/assets/847f04cf-501a-497d-b7ed-8c2d417ee080" />

<img width="1198" height="2531" alt="Screenshot_20250722_103649" src="https://github.com/user-attachments/assets/4349897a-3dc8-4aa7-8f48-dcf1ca8ad6bc" />
<img width="1198" height="2531" alt="Screenshot_20250722_103754" src="https://github.com/user-attachments/assets/75bdbc71-29b1-48fe-beef-4f456af308e6" />





## Features

### Live Clock Widget

- Displays the current time in real-time
- Updates automatically every second
- Clean digital format: "HH:mm:ss"
- Uses custom Orbitron font for aesthetic appeal
- Large, centered, readable display

### Animated Background

- Smooth, continuous animated gradient background
- Slowly shifting colors with diagonal movement
- Calm, modern, non-distracting ambient feel
- Built with Jetpack Compose's Canvas API
- Uses `rememberInfiniteTransition` for smooth animations

## Technical Details

- **Platforms**: Desktop (JVM) and Android
- **Framework**: Compose Multiplatform with Material 3
- **Architecture**: Clean separation of concerns
  - `AnimatedBackground.kt` - Gradient animation component
  - `BasicClockWidget.kt` - Real-time clock display
  - `MainScreen.kt` - Main UI that combines both components
- **Time Handling**: Platform-specific implementations using expect/actual pattern
- **Font**: Custom Orbitron TTF font bundled in resources

## Project Structure

```
composeApp/src/
├── commonMain/
│   ├── kotlin/com/eid/onstand/
│   │   ├── App.kt                  # Main app entry point
│   │   ├── MainScreen.kt          # Main screen combining components
│   │   ├── AnimatedBackground.kt   # Animated gradient background
│   │   └── BasicClockWidget.kt         # Live clock widget
│   └── composeResources/
│       └── font/
│           └── digital_clock.ttf  # Custom font
├── androidMain/kotlin/com/eid/onstand/
│   └── Platform.android.kt        # Android time implementation
└── desktopMain/kotlin/com/eid/onstand/
    ├── Platform.desktop.kt        # Desktop time implementation
    └── main.kt                    # Desktop app launcher
```

## How to Run

### Desktop

```bash
./gradlew :composeApp:runDesktop
```

### Android

```bash
./gradlew :composeApp:installDebug
```

## Requirements

- JDK 11 or higher
- Android SDK (for Android target)
- Gradle 8.0+

## License

This project is part of the OnStand MVP demonstration.
