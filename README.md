# OnStand

A customizable dock clock application built with Compose Multiplatform for Android, iOS, and Desktop platforms. Transform your device into an elegant timepiece with smooth animations and visual effects.

## Android

<p align="center">
  <img width="2531" height="1198" alt="Screenshot_20250726_202220" src="https://github.com/user-attachments/assets/35f7bcfb-e0a9-44fc-840e-5e5309fc1a32"  width="45%" />
<img width="2531" height="1198" alt="Screenshot_20250726_202404" src="https://github.com/user-attachments/assets/431c556a-f8b8-4020-a9ce-3d1254eef462" width="45%"/>
</p>



## IOS

<p align="center">
  <img width="2752" height="2064" alt="image" src="https://github.com/user-attachments/assets/748ef113-9c0a-4c19-ac32-63a336a93d23" width="45%"/>
<img width="2752" height="2064" alt="image" src="https://github.com/user-attachments/assets/97f9f5c8-0ee8-4c3d-8d92-7bd013c29eee"  width="45%"/>

</p>


## ✨ Features

### Clock Styles

- **Digital Clock** - Modern digital display with customizable fonts
- **Analog Clock** - Classic circular clock face
- Additional styles in development

### Background Options

- **Dynamic Shaders** - Real-time visual effects using SKSL
- **Gradient Backgrounds** - Smooth color transitions
- **Animated Effects** - Rotating gradients, fog, and wave animations
- **Solid Colors** - Simple, elegant color options

### Customization

- **Live Preview** - See changes in real-time
- **Font Selection** - Choose from multiple typography options
- **Color Palette** - Sophisticated colors optimized for clock displays
- **Persistent Settings** - Your preferences are saved automatically

### Platform Support

- **Android** - Native Android application
- **iOS** - Native iOS application  
- **Desktop** - macOS, Windows, and Linux

## Architecture

Built with clean architecture principles using a feature-based module structure.

```
composeApp/
├── core/           # Core utilities, DI, models, and theme
├── data/           # Data persistence layer
└── feature/        # UI features
    ├── backgrounds/    # Background effects
    ├── customization/  # Settings UI
    ├── home/          # Main clock display
    └── widgets/       # Clock implementations
```

## Technical Stack

- **Compose Multiplatform** - Cross-platform UI framework
- **Koin** - Dependency injection
- **DataStore** - Settings persistence
- **SKSL Shaders** - Custom visual effects
- **Haze** - Glass morphism effects

## Getting Started

### Prerequisites

- JDK 11+
- Android Studio or IntelliJ IDEA
- Xcode 14+ (for iOS)

### Running the App

#### Desktop

```bash
./gradlew :composeApp:runDesktop
```

#### Android

```bash
./gradlew :composeApp:installDebug
```

#### iOS

Open `iosApp/iosApp.xcodeproj` in Xcode and run the project.

### Building

```bash
# Desktop distributable
./gradlew :composeApp:createDistributable

# Android release APK
./gradlew :composeApp:assembleRelease
```

## 🔧 Development

### Adding New Clock Types

1. Create clock composable in `feature/widgets/clocks/`
2. Add `ClockType` case in `CustomizationModels.kt`
3. Update `BackgroundClockView.kt` and preview components
4. Add to `ClockRepository.getClockTypes()`

### Adding New Backgrounds

1. Create shader/animation in appropriate `feature/backgrounds/` subdirectory
2. Add `ShaderType` enum value in `CustomizationModels.kt`
3. Update preview cards and background view components
4. Add entries to `BackgroundRepository` methods

### Project Structure

The project uses a clean architecture approach with:

- **Core**: Shared utilities, DI, and models
- **Data**: Repository pattern for data access
- **Feature**: Feature-based organization with UI and business logic
- **Platform-specific**: Actual implementations for platform differences

## 📦 Dependencies

Key libraries used in the project:

- **Compose Multiplatform** - UI framework
- **Material 3** - Design system
- **Koin** - Dependency injection
- **kotlinx-datetime** - Date/time handling
- **DataStore** - Preferences storage
- **Haze** - Blur and frosting effects
- **kotlinx-serialization** - Data serialization

## 🤝 Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and
feature requests.


---
