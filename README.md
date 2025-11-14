# DiscipleshipSignup

An Android app for collecting discipleship group sign-ups and sending them via email.

## Requirements

- Android Studio Ladybug (or newer) with Android Gradle Plugin 8.12.1
- JDK 17 (bundled with recent Android Studio) or compatible with AGP 8.x
- Android SDK platforms and build tools for API 34
- A device or emulator running Android 5.0 (API 21) or higher

## Project Info

- Application ID: `com.example.discipleshipsignup`
- Min SDK: 21
- Target/Compile SDK: 34
- Kotlin: 1.8.20 (via plugin)
- Build system: Gradle Kotlin DSL

## Getting Started

### Open in Android Studio

1. File > Open… and select the project root folder `DiscipleshipSignup`.
2. Let Gradle sync complete (downloads dependencies).
3. If prompted to install SDK 34 or build tools, accept.

### Run on a device or emulator

1. Connect an Android device with USB debugging enabled, or start an emulator.
2. Select a run target from the device chooser.
3. Click Run ▶ or use Shift+F10.

### Build from the command line

From the project root:

```bash
./gradlew clean
./gradlew :app:assembleDebug  # builds debug APK at app/build/outputs/apk/debug/
./gradlew :app:installDebug   # installs on a connected device/emulator
./gradlew :app:connectedAndroidTest  # runs instrumented tests on a connected device/emulator
./gradlew :app:testDebugUnitTest     # runs local unit tests
```

On Windows, use `gradlew.bat`.

## Tests

- Unit tests (JVM): `app/src/test/`
  - Run: `./gradlew :app:testDebugUnitTest`
- Instrumented tests (on device/emulator): `app/src/androidTest/`
  - Run: `./gradlew :app:connectedAndroidTest`

You can also right-click the test directories or classes in Android Studio and choose Run.

## App Behavior

- Entry point: `MainActivity`
- Layout: `res/layout/activity_main.xml`
- Users enter name and email, choose gender, day, time, and group type, then tap Submit.
- The app opens an email chooser with a prefilled email to `kevin@harbourfellowship.com` containing the submission details.

Note: The email recipient can be changed in `MainActivity.kt` (look for `Intent.EXTRA_EMAIL`).

## Release Builds and Signing

Release builds require a signed APK/AAB.

### Create a keystore

```bash
keytool -genkeypair -v -keystore release.keystore -alias discipleship -keyalg RSA -keysize 2048 -validity 10000
```

Remember the keystore path, alias, and passwords.

### Configure signing in Gradle (recommended: use `gradle.properties`)

Add to your user `~/.gradle/gradle.properties` (not checked in):

```
RELEASE_STORE_FILE=/absolute/path/to/release.keystore
RELEASE_STORE_PASSWORD=your-store-password
RELEASE_KEY_ALIAS=discipleship
RELEASE_KEY_PASSWORD=your-key-password
```

Then update `app/build.gradle.kts` signing config (example):

```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("RELEASE_STORE_FILE") ?: project.findProperty("RELEASE_STORE_FILE") as String)
            storePassword = System.getenv("RELEASE_STORE_PASSWORD") ?: project.findProperty("RELEASE_STORE_PASSWORD") as String
            keyAlias = System.getenv("RELEASE_KEY_ALIAS") ?: project.findProperty("RELEASE_KEY_ALIAS") as String
            keyPassword = System.getenv("RELEASE_KEY_PASSWORD") ?: project.findProperty("RELEASE_KEY_PASSWORD") as String
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

Alternatively, configure signing via Android Studio (Build > Generate Signed Bundle / APK…).

### Build a release APK

```bash
./gradlew :app:assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

### Build a release App Bundle (AAB)

```bash
./gradlew :app:bundleRelease
# Output: app/build/outputs/bundle/release/app-release.aab
```

## Troubleshooting

- SDK/platform tools not found: Open SDK Manager and install Android 34 (API 34) and Build Tools.
- Gradle sync fails after IDE update: File > Invalidate Caches / Restart in Android Studio.
- Device not detected: Ensure USB debugging is enabled, correct drivers (Windows), or use an emulator.
- Kotlin/AGP version mismatch: This project pins AGP 8.12.1 and Kotlin 1.8.20 plugin at the module; align if you upgrade.

## Project Structure

- `app/src/main/java/com/example/discipleshipsignup/MainActivity.kt` — activity logic and email intent
- `app/src/main/res/layout/activity_main.xml` — UI layout
- `app/build.gradle.kts` — Android module config, dependencies
- `build.gradle.kts` — top-level Gradle settings
- `settings.gradle.kts` — repositories and project settings

## License

Add your license here if applicable.
