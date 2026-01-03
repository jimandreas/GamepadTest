# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

GamepadTest is an Android app that displays connected Bluetooth gamepad state (joysticks, buttons, D-pad, triggers). Built with Kotlin, targeting Android 7.0+ (SDK 24-36).

## Build Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run unit tests
./gradlew test

# Run instrumentation tests (requires device/emulator)
./gradlew connectedCheck
```

## Architecture

**MVVM pattern** with Fragment-based navigation:

- **MainActivity** - Intercepts all gamepad input via `onKeyDown()`/`onKeyUp()` (buttons) and `onGenericMotionEvent()` (joysticks/triggers/D-pad), forwarding to service layer
- **gamepad/** - Service layer with singleton services accessed via `GamepadServices` object
  - `GamepadJoysticks` - Joystick input processing
  - `GamepadButton` - Button input processing
  - `GamepadLogger` - Event logging
- **ui/** - Fragments with ViewModels using LiveData
  - `GamepadFragment/ViewModel` - Main gamepad visualization
  - `LogFragment/ViewModel` - Event log display
  - `ScanFragment/ViewModel` - Device scanning
  - `SettingsFragment` - Preferences
- **ui/customviews/JoystickView** - Custom joystick visualization component

Navigation uses AndroidX Navigation with bottom nav bar (4 destinations).

## Key Configuration

- **Build:** Gradle Kotlin DSL, AGP 8.13.0, Kotlin 2.2.21
- **Dependencies:** Version catalog at `gradle/libs.versions.toml`
- **Build features:** View binding and data binding enabled
- **Code style:** `kotlin.code.style=official` (gradle.properties)

## Known Issues

- Bluetooth connection toggles between regular device and "Input Device" modes
- Rumble/vibration not available (Android limitation)
- Xbox controller keymapping varies across Android versions
