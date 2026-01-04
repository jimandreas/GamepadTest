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

# Run all unit tests
./gradlew test

# Run a specific test class
./gradlew test --tests "com.bammellab.gamepadtest.ExampleTest"

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
  - `LocalBroadcastReceiver` - Bluetooth state change handling
- **ui/** - Fragments with ViewModels using LiveData
  - `GamepadFragment/ViewModel` - Main gamepad visualization
  - `LogFragment/ViewModel` - Event log display
  - `ScanFragment/ViewModel` - Device scanning
  - `SettingsFragment` - Preferences
- **ui/customviews/JoystickView** - Custom joystick visualization component

**Data flow:** MainActivity intercepts hardware input → GamepadServices singletons process events → ViewModels observe via LiveData → Fragments update UI.

Navigation uses AndroidX Navigation with bottom nav bar (4 destinations defined in `navigation_mobile.xml`).

## Key Configuration

- **Build:** Gradle Kotlin DSL, AGP 8.13.2, Kotlin 2.2.21
- **Dependencies:** Version catalog at `gradle/libs.versions.toml`
- **Build features:** View binding, data binding, and BuildConfig enabled
- **Code style:** `kotlin.code.style=official` (gradle.properties)
- **Test framework:** JUnit 5 (Jupiter) configured via `useJUnitPlatform()`

## Known Issues

- Bluetooth connection toggles between regular device and "Input Device" modes (must be in Input Device mode to work)
- Rumble/vibration not available (Android limitation - see [issuetracker.google.com/issues/128314303](https://issuetracker.google.com/issues/128314303))
- Xbox controller keymapping varies across Android versions
