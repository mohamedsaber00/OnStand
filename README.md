# OnStand

A beautiful Compose Multiplatform (KMP) live clock app with animated backgrounds.

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
  - `ClockWidget.kt` - Real-time clock display
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
│   │   └── ClockWidget.kt         # Live clock widget
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