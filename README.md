# MoodTunes (Mood Music)

This Android app plays local audio files according to mood selection. I added Firebase Authentication (email/password) and a splash screen. This repository is configured to use Kotlin and the Android Gradle plugin versions used in the project.

## Quick setup

1. Open the project in Android Studio.
2. Make sure you have the Android SDK and Kotlin configured (this project uses Kotlin 1.9.24).
3. Ensure `google-services.json` is in `app/` (it already appears to be present in this repo).
4. In case you haven't set up Firebase project, enable Email/Password sign-in in the Firebase Console for your app.

## How to change the app icon / placeholder to your logo

Place your logo in the appropriate `res` folder under `app/src/main/res`.

- For a simple drawable (PNG):
  - Put the file in `app/src/main/res/drawable/` (recommended for generic images).
  - Or for launcher icons, put appropriate densities into `mipmap-<density>` folders:
    - `app/src/main/res/mipmap-mdpi/`
    - `app/src/main/res/mipmap-hdpi/`
    - `app/src/main/res/mipmap-xhdpi/`
    - `app/src/main/res/mipmap-xxhdpi/`
    - `app/src/main/res/mipmap-xxxhdpi/`

Example: if your logo is in your Downloads as `my_logo.png`:

1. Copy it to the project: `app/src/main/res/drawable/my_logo.png` (or to a mipmap folder for launcher icon).
2. Reference it in layouts or manifest as `@drawable/my_logo` (or `@mipmap/my_logo` if placed in mipmap).

If you want a proper adaptive launcher icon, use Android Studio's Image Asset tool: "Right click `app` → New → Image Asset" and follow the steps.

## Build & Run

From the project root you can build and run with Gradle or via Android Studio.

Common commands (Windows PowerShell):

```powershell
./gradlew assembleDebug
```

## GitHub push performed by the assistant

I will (or already) attempted to add this README and push the repository to the remote you provided. If you need a different remote URL, run:

```powershell
git remote set-url origin https://github.com/USERNAME/REPO.git
```

## Notes

- The repo includes `google-services.json`. Keep that file private to your project only.
- For any Firebase setup issues, confirm your package name matches the Firebase app configuration.

If you want, I can also add launcher icon assets to multiple densities if you provide the high-resolution source image.

