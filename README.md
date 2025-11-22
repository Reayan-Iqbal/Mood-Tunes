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

## App Screenshots

## 1 - SIGN UP MODULE

<img width="456" height="877" alt="image" src="https://github.com/user-attachments/assets/47448bdd-907c-4388-9cfe-5bc2f834e130" />
  
## 2 - LOGIN MODULE
<img width="508" height="861" alt="image" src="https://github.com/user-attachments/assets/a6eb6b51-51bf-43db-84c5-f418f20ceb82" />

## 3 - EVENT MODULE

<img width="421" height="585" alt="image" src="https://github.com/user-attachments/assets/98579a30-a694-4a7f-b787-7965a7630a63" />

## 4 - SPLASH MODULE

<img width="479" height="607" alt="image" src="https://github.com/user-attachments/assets/30fbea38-a7d5-41cb-a8c6-3363d8391ca9" />

## 5 - TRACK MODULE

<img width="420" height="750" alt="image" src="https://github.com/user-attachments/assets/3dadb528-a915-44e7-a1b8-d6128de10619" />

## 6 - MENU MODULE

<img width="459" height="819" alt="image" src="https://github.com/user-attachments/assets/95cf048b-fdf6-4b18-b3d6-ef8907bd0fed" /> 


## Notes

- The repo includes `google-services.json`. Keep that file private to your project only.
- For any Firebase setup issues, confirm your package name matches the Firebase app configuration.

If you want, I can also add launcher icon assets to multiple densities if you provide the high-resolution source image.

