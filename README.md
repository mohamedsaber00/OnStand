# OnStand - "Under development"

A beautiful, customizable dock clock application built with Compose Multiplatform, supporting
Android, iOS, and Desktop platforms. Transform your screen into an elegant timepiece with fluid
animations, stunning visual effects, and extensive customization options.

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

### 🕐 Multiple Clock Styles

- **Digital Clock** - Clean, modern digital display
- **Analog Clock** - Classic circular clock with smooth hour/minute hands
- **Digital Segments** - Retro-style 7-segment LCD display
- **Morph Flip Clock** - Elegant flip animation between numbers

### 🎨 Rich Background Options

#### Shader Effects

- **Space Shader** - Cosmic nebula with twinkling stars
- **Purple Gradient** - Smooth flowing purple gradients
- **Glowing Ring** - Pulsating ethereal ring effects
- **Moving Triangles** - Geometric patterns in motion
- **Purple Smoke** - Mystical smoke-like effects
- **Palette Shader** - Dynamic color transitions
- **Ether Shader** - Abstract energy flows
- **Moving Waves** - Fluid wave animations

#### Live Animations

- **Rotating Gradient** - Slowly rotating color gradients
- **Fog Effect** - Atmospheric fog with particle effects
- **Wave Background** - Wave movements

### 🎛️ Customization System

- **Interactive Preview** - Real-time customization with live preview
- **Font Options** - Multiple typography choices (Roboto, Serif, Monospace, Cursive)
- **Color Themes** - Extensive color palette for clock elements
- **Background Selection** - Easy switching between visual effects
- **Persistent Settings** - Preferences saved using DataStore

### 📱 Cross-Platform Support

- **Android** - Native Android application
- **iOS** - Native iOS application
- **Desktop** - macOS, Windows, and Linux support

## 🏗️ Architecture

A simple architecture, No navigation library

```
composeApp/src/commonMain/kotlin/com/eid/onstand/
├── core/
│   ├── di/              # Dependency injection with Koin
│   ├── models/          # Data models and customization types
│   ├── shaders/         # Shader utilities and effects
│   ├── theme/           # Material 3 theming
│   └── utils/           # Common utilities
├── data/
│   └── date/            # Platform-specific time handling
├── feature/
│   ├── backgrounds/     # Background effects and shaders
│   │   ├── compose/     # Compose-based animations
│   │   └── shader/      # Custom shader effects
│   ├── preview/         # Customization screen and preview
│   │   └── components/  # Reusable preview components
│   └── widgets/
│       └── clocks/      # Clock widget implementations
├── App.kt               # Main application entry point
└── AppViewModel.kt      # Main app state management
```

## 🛠️ Technical Stack

- **Framework**: Compose Multiplatform
- **State Management**: ViewModel + StateFlow
- **Dependency Injection**: Koin
- **Animations**: Compose Animation APIs + Custom SKSL Shaders 
- **Time Handling**: kotlinx-datetime with platform-specific implementations
- **Persistence**: DataStore for settings
- **UI Effects**: Haze for blur/frosting effects

## 🚀 Getting Started

### Prerequisites

- JDK 11 or higher
- Android SDK (for Android builds)
- Xcode (for iOS builds)
- Gradle 8.0+

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

### Building for Distribution

#### Desktop Package

```bash
./gradlew :composeApp:createDistributable
```

#### Android APK

```bash
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
