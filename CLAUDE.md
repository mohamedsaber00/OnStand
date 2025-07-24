# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

OnStand is a cross-platform dock clock application built with Compose Multiplatform. It targets Android, iOS, and Desktop platforms with customizable clock styles and animated backgrounds.

## Development Commands

### Building and Running
```bash
# Desktop
./gradlew :composeApp:runDesktop
./gradlew :composeApp:createDistributable

# Android
./gradlew :composeApp:installDebug
./gradlew :composeApp:assembleRelease

# iOS - Build through Xcode
open iosApp/iosApp.xcodeproj
```

### Common Development Tasks
```bash
# Clean build
./gradlew clean

# Build all targets
./gradlew build

# Check for dependency updates
./gradlew dependencyUpdates
```

## Architecture Overview

The project follows clean architecture with MVVM pattern and feature-based organization:

### Core Architecture Components

1. **State Management**: ViewModels with StateFlow and unidirectional data flow
2. **Dependency Injection**: Koin modules in `core/di/` with platform-specific configurations
3. **Data Persistence**: DataStore for user preferences via repositories
4. **Platform Abstraction**: `expect`/`actual` declarations for platform-specific implementations

### Code Structure

- `core/`: Infrastructure code (DI, models, UI utilities, repositories)
- `feature/`: Feature modules organized by functionality
  - `backgrounds/`: Background effect implementations (shaders and animations)
  - `preview/`: Customization UI and preview system
  - `widgets/`: Clock widget implementations
- `data/`: Additional data layer components

### Adding New Features

**New Clock Widget**:
1. Create widget in `feature/widgets/clocks/`
2. Implement `ClockWidget` interface
3. Add to `ClockWidget.Companion.entries` list
4. Follow existing patterns from other clock implementations

**New Background Effect**:
1. For shaders: Add SKSL file to `composeApp/src/commonMain/composeResources/files/`
2. For animations: Create in `feature/backgrounds/compose/`
3. Add to `BackgroundEffect.entries` list
4. Implement preview in background selection UI

### Key Patterns

- **Navigation**: State-based navigation without navigation library
- **Preview System**: Real-time customization with immediate visual feedback
- **Shader Integration**: Custom SKSL shaders loaded via resource system
- **Platform Features**: Use `expect`/`actual` for platform-specific functionality

### Important Notes

- No tests currently exist - consider adding when implementing new features
- Font resources go in `composeResources/font/`
- All UI strings should be added to `Res.string` for potential localization
- Settings are persisted via `SettingsRepository` using DataStore