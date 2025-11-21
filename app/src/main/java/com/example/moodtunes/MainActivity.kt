package com.example.moodtunes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val moods = listOf("Happy", "Sad", "Chill", "Energetic", "Focused")
    private lateinit var allTracks: List<Track>
    private lateinit var adapter: TrackAdapter
    private lateinit var playerManager: PlayerManager

    private lateinit var tvNowPlaying: TextView
    private lateinit var btnPlayPause: ImageButton
    private lateinit var btnSpeed: Button

    // Speed cycle (1x -> 1.5x -> 2x)
    private val speeds = listOf(1.0f, 1.5f, 2.0f)
    private var currentSpeedIndex = 0

    // Keep last played track so play/pause works
    private var lastPlayed: Track? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        playerManager = PlayerManager(this)

        // Your tracks (make sure res/raw filenames match these names)
        allTracks = listOf(
            Track("1", "Bound 2", "Kanye West", "bound_2", "Romantic"),
            Track("2", "Can't Get You Out of My Head", "Kylie Minogue", "cant_get_you_out_of_my_head", "Dance"),
            Track("3", "Money Trees", "Kendrick Lamar ft. Jay Rock", "money_trees", "Chill"),
            Track("4", "Ophelia", "The Lumineers", "ophelia", "Happy"),
            Track("5", "Roxanne", "Arizona Zervas", "roxanne", "Energetic")
        )

        tvNowPlaying = findViewById(R.id.tv_nowplaying)
        btnPlayPause = findViewById(R.id.btn_playpause)
        btnSpeed = findViewById(R.id.btn_speed_normal)

        // Setup mood buttons
        val moodContainer = findViewById<LinearLayout>(R.id.moodContainer)
        moods.forEach { mood ->
            val btn = Button(this).apply {
                text = mood
                setOnClickListener {
                    filterByMood(mood)
                }
            }
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.marginEnd = 12
            moodContainer.addView(btn, lp)
        }

        // RecyclerView + adapter
        val rv = findViewById<RecyclerView>(R.id.rv_tracks)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = TrackAdapter(allTracks) { track -> playTrack(track) }
        rv.adapter = adapter

        // Play/pause behaviour
        btnPlayPause.setOnClickListener {
            if (playerManager.isPlaying()) {
                playerManager.pause()
                btnPlayPause.setImageResource(android.R.drawable.ic_media_play)
                tvNowPlaying.text = "Paused"
            } else {
                // if something was played before, resume that track; else play first track
                val trackToPlay = lastPlayed ?: allTracks.firstOrNull()
                trackToPlay?.let {
                    playTrack(it)
                }
            }
        }

        // Speed button initial label and click cycle
        btnSpeed.text = "${speeds[currentSpeedIndex]}x"
        btnSpeed.setOnClickListener {
            currentSpeedIndex = (currentSpeedIndex + 1) % speeds.size
            val newSpeed = speeds[currentSpeedIndex]
            playerManager.setSpeed(newSpeed)
            btnSpeed.text = "${newSpeed}x"
        }
    }

    override fun onStart() {
        super.onStart()
        // Redirect to login if user not authenticated
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun filterByMood(mood: String) {
        val filtered = allTracks.filter { it.mood == mood }
        // create new adapter with filtered list (keeps code simple and consistent with your TrackAdapter)
        val rv = findViewById<RecyclerView>(R.id.rv_tracks)
        adapter = TrackAdapter(filtered) { track -> playTrack(track) }
        rv.adapter = adapter
    }

    private fun playTrack(track: Track) {
        lastPlayed = track
        playerManager.playRaw(track.resRawName)
        btnPlayPause.setImageResource(android.R.drawable.ic_media_pause)
        tvNowPlaying.text = "${track.title} — ${track.artist}"
        // ensure speed button shows active speed (in case user changed speed earlier)
        btnSpeed.text = "${playerManager.currentSpeed}x"
    }

    override fun onDestroy() {
        super.onDestroy()
        playerManager.release()
    }
}
