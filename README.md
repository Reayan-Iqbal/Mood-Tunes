# MoodTunes (Minimal Android project)

This is a minimal Android Studio project scaffold for **MoodTunes**:
- Kotlin, XML layouts
- Manual mood selector (buttons)
- Local demo audio files under `app/src/main/res/raw/` (sample1.wav, sample2.wav)
- ExoPlayer usage to play raw resources

How to open:
1. Download `MoodTunes.zip`.
2. Open in Android Studio (File -> Open) and choose the folder.
3. Let Gradle sync. Build & run on a device or emulator (ensure API >= 21).

Notes:
- This is a simple MVP to get you started. It intentionally keeps things straightforward:
  - No Room DB, no background MediaService, no network API.
  - If you want background playback, notification controls, or a Compose UI next, I can add them.
