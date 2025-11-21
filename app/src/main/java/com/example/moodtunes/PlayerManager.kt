package com.example.moodtunes

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackParameters

class PlayerManager(private val context: Context) {

    private var player: ExoPlayer? = null

    var currentSpeed = 1.0f
        private set

    fun prepare() {
        if (player == null) {
            player = ExoPlayer.Builder(context)
                .setHandleAudioBecomingNoisy(true)
                .build()

            // ⭐ CRITICAL FIX — disable automatic time-stretching
            player?.playbackParameters = PlaybackParameters(1f, 1f)
        }
    }

    fun playRaw(resName: String) {
        prepare()

        val rid = context.resources.getIdentifier(resName, "raw", context.packageName)
        if (rid == 0) return

        val uri = Uri.parse("android.resource://${context.packageName}/$rid")
        val item = MediaItem.fromUri(uri)

        player?.setMediaItem(item)
        player?.prepare()

        // ⭐ ALWAYS enforce correct pitch
        player?.playbackParameters = PlaybackParameters(currentSpeed, 1f)

        player?.play()
    }

    fun pause() {
        player?.pause()
    }

    fun setSpeed(speed: Float) {
        currentSpeed = speed
        // ⭐ MUST keep pitch at 1.0f
        player?.playbackParameters = PlaybackParameters(speed, 1f)
    }

    fun release() {
        player?.release()
        player = null
    }

    fun isPlaying(): Boolean = player?.isPlaying ?: false
}
