# OnStand

A customizable dock clock application built with Compose Multiplatform for Android, iOS, and Desktop platforms. Transform your device into an elegant timepiece with smooth animations and visual effects.

<p align="center">
  <a href="https://github.com/Subfly/onstand">
    <img src="https://img.shields.io/github/stars/Subfly/onstand?style=social" alt="GitHub stars">
  </a>
</p>

> ⭐ **If you find this project useful, please consider giving it a star!**

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

## Development

### Adding New Features

**Clock Widgets**
1. Extend `ClockWidget` in `feature/widgets/clocks/`
2. Register in `ClockRegistry`
3. Implement the `Render()` composable

**Background Effects**
1. Extend `BackgroundEffect` in `feature/backgrounds/`
2. Register in `BackgroundRegistry`
3. For shaders, add SKSL files to `composeResources/files/`

## Key Dependencies

- Compose Multiplatform
- Koin (DI)
- DataStore (Persistence)
- Haze (UI Effects)
- kotlinx-datetime
- kotlinx-serialization

## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and feature requests.

## License

This project is currently under development. License details will be added soon.
