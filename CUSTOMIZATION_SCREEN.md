# Clock Customization Screen

## Overview

This implementation creates a comprehensive clock and background customization screen for the
OnStand dock clock app, featuring real-time previews, animated shader backgrounds, and intuitive
clock selection. The customization screen provides users with extensive options to personalize
their clock display with live previews and authentic clock widgets.

## Features

### 🎨 Background Customization

- **Gradient backgrounds** with multiple color combinations
- **Animated shader backgrounds** with real-time effects:
    - **Ether** - Ethereal flowing patterns
    - **Glowing Ring** - Pulsating ring effects
    - **Moving Triangles** - Dynamic geometric patterns
    - **Purple Flow** - Flowing purple gradients
    - **Space** - Cosmic space effects
    - **Moving Waves** - Layered wave animations
    - **Turbulence** - Complex 3D turbulence effects
- **Live animated backgrounds** with dynamic elements:
    - **Rotating Gradient** - Continuously rotating color gradients
    - **Fog Effect** - Atmospheric fog animation
    - **Animated Particles** - Dynamic particle systems
- **Solid colors** (black, white, and more)

### 🕐 Clock Style Options

- **Digital Modern** - Clean digital display
- **Digital + Seconds** - Digital with seconds counter
- **Digital Segments** - 7-segment display style
- **Analog Classic** - Traditional analog clock with hands
- **Flip Clock** - Retro flip-card style display
- **Minimal** - Simplified clean design

### 🔧 Interactive Clock Controls

- **Seconds Toggle** - Show/hide seconds for digital clocks
- **Real-time Preview** - All clock faces update every second
- **Font Family Support** - Different font styles per clock type
- **Color Integration** - All clocks use selected font colors

### 🌈 Font & Color Customization

- **Modern** - Clean white with subtle secondary colors
- **Classic** - Traditional black with gray accents
- **Bold Blue** - Vibrant blue theme
- **Elegant Gold** - Luxurious gold styling
- **Green Digital** - Matrix-style green display
- **Purple Glow** - Purple gradient theme

### 📱 Layout Options

- **Previous Minutes** - Shows faded previous/next minutes
- **Clean Center** - Minimalist centered time display
- **Left Aligned** - Time positioned to the left
- **Right Aligned** - Time positioned to the right

## Architecture

### Data Models

- `BackgroundOption` - Represents different background types including shaders
- `ClockStyle` - Defines clock appearance and behavior
- `FontColorOption` - Manages color schemes and font styles
- `LayoutOption` - Controls time display positioning and additional elements
- `CustomizationState` - Maintains current user selections
- `ShaderType` - Enum for different shader background types

### Repository Pattern

- `BackgroundRepository` - Manages background options and shader configurations
- `ClockRepository` - Handles clock styles, font colors, and layout options
- `CustomizationRepository` - Coordinates between repositories and manages state
- Reactive state management with Kotlin coroutines

### UI Components

- `ClockPreview` - Real-time preview using actual clock widgets
- `BackgroundSelector` - Horizontal scrollable background options with live shader previews
- `ClockStyleSelector` - Horizontal scrollable clock selection with live clock faces
- `FontColorSelector` - Horizontal scrollable color theme selection
- `ClockFacePreview` - Small-scale live clock previews for selection
- `CustomizationOptions` - Reusable UI components

### State Management

- `PreviewViewModel` - MVVM pattern with reactive state
- Koin dependency injection for clean architecture
- Proper state flow management and lifecycle handling
- Seconds toggle functionality for digital clocks

## Technical Implementation

### Key Features

1. **Real-time Previews** - All clocks and backgrounds update in real-time
2. **Authentic Widgets** - Uses actual clock components, not fake previews
3. **Animated Shaders** - Live shader backgrounds in preview buttons
4. **Intuitive UI** - Horizontal scrollable selection similar to modern apps
5. **Seconds Toggle** - Interactive switch for digital clock seconds
6. **Smooth Animations** - Fluid transitions and selection feedback
7. **Accessibility** - Proper content descriptions and navigation
8. **Responsive Design** - Works across different screen sizes

### Dependencies Used

- **Compose Multiplatform** - Cross-platform UI framework
- **Koin** - Dependency injection
- **Kotlinx DateTime** - Time handling
- **Material 3** - Modern UI components
- **Coroutines** - Asynchronous programming
- **HypnoticCanvas** - Shader background rendering

## Usage

### Navigation

1. Tap the settings gear (⚙️) button on the main screen
2. Customize your clock using the various options
3. See real-time previews of all changes
4. Use "Apply" to save changes or "Cancel" to discard
5. Back navigation returns to the main clock screen

### Customization Process

1. **Preview** - See real-time changes with actual clock widgets at the top
2. **Background** - Select from animated shaders, gradients, live effects, or solid colors
3. **Clock Style** - Choose from live preview buttons showing actual clock faces
4. **Seconds Toggle** - Enable/disable seconds for digital clocks
5. **Font & Color** - Pick your preferred color scheme with live preview
6. **Apply Changes** - Save your customizations

## Shader Backgrounds

### Available Shaders

- **Ether** - Flowing ethereal patterns with smooth animations
- **Glowing Ring** - Pulsating ring effects with dynamic lighting
- **Moving Triangles** - Geometric patterns with continuous movement
- **Purple Flow** - Smooth purple gradient flows
- **Space** - Cosmic space effects with stars and nebulae
- **Moving Waves** - Layered wave animations with ocean-like effects
- **Turbulence** - Complex 3D turbulence with swirling distortions

### Technical Details

- All shaders use SkSL (Skia Shading Language)
- Real-time rendering with 60fps performance
- Proper time uniforms for smooth animations
- Optimized for mobile devices

## Future Enhancements

### Planned Features

- **Custom color picker** for unlimited color options
- **Font family selection** with system fonts
- **Background image uploads** for personal photos
- **Animation speed controls** for shader backgrounds
- **Sound themes** for hourly chimes
- **Widget size options** for different screen configurations
- **Theme presets** - Pre-defined style combinations

### Technical Improvements

- **Persistence** - Save user preferences locally
- **Cloud sync** - Sync settings across devices
- **Performance optimization** - Efficient rendering for complex shaders
- **Additional shaders** - More animated background options
- **Advanced layouts** - More positioning options

## Code Structure

```
feature/preview/
├── PreviewScreen.kt              # Main customization screen
├── PreviewViewModel.kt           # State management with seconds toggle
├── components/
│   ├── ClockPreview.kt          # Live main preview with actual widgets
│   ├── ClockFacePreview.kt      # Small live clock previews
│   ├── CustomizationOptions.kt  # UI option components with live shaders
│   └── BackgroundSelector.kt    # Background selection with shader previews
core/
├── models/
│   └── CustomizationModels.kt   # Data models with shader types
├── data/
│   ├── BackgroundRepository.kt  # Background and shader management
│   ├── ClockRepository.kt       # Clock styles and font colors
│   └── CustomizationRepository.kt # Unified state management
└── di/
    └── AppModule.kt             # Dependency injection
feature/backgrounds/shader/
├── EtherShader.kt               # Ethereal flowing patterns
├── GlowingRing.kt               # Pulsating ring effects
├── MovingTrianglesShader.kt     # Dynamic geometric patterns
├── PurpleGradientShader.kt      # Purple gradient flows
├── SpaceShader.kt               # Cosmic space effects
├── MovingWaveShader.kt          # Layered wave animations
└── TurbulenceShader.kt          # 3D turbulence effects
```

## Design Principles

### User Experience

- **Immediate feedback** - All changes visible instantly with real previews
- **Intuitive controls** - Familiar horizontal scrolling selection
- **Visual hierarchy** - Clear organization with proper spacing
- **Consistent styling** - Cohesive design language throughout
- **Interactive elements** - Responsive buttons and smooth animations

### Technical Excellence

- **Clean architecture** - Clear separation of concerns
- **Testable code** - Proper dependency injection
- **Performance focus** - Efficient UI updates and shader rendering
- **Maintainable structure** - Well-organized code with clear responsibilities
- **Real-time accuracy** - Authentic previews using actual widgets

## Recent Improvements

### Major Updates

1. **Replaced fake previews** with actual clock widgets
2. **Added animated shader backgrounds** with real-time rendering
3. **Implemented horizontal scrollable selection** for all options
4. **Added seconds toggle** for digital clocks
5. **Enhanced preview system** with live updates
6. **Improved state management** with proper repository pattern
7. **Added two new shaders** - Moving Waves and Turbulence

### Technical Enhancements

- **Repository separation** - Split into BackgroundRepository and ClockRepository
- **Live shader previews** - Real animated backgrounds in selection buttons
- **Proper font color integration** - All widgets use selected colors
- **Responsive design** - Better layout for different screen sizes
- **Performance optimization** - Efficient shader rendering

This implementation provides a comprehensive, visually appealing, and highly functional clock
customization experience that rivals modern mobile app interfaces while maintaining excellent
performance and code quality.