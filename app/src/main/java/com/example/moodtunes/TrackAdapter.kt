package com.example.moodtunes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrackAdapter(
    private val items: List<Track>,
    private val onPlayClicked: (Track) -> Unit
) : RecyclerView.Adapter<TrackAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_title)
        val artist: TextView = view.findViewById(R.id.tv_artist)
        val btnPlay: Button = view.findViewById(R.id.btn_play)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val t = items[position]
        holder.title.text = t.title
        holder.artist.text = "${t.artist} • ${t.mood}"
        holder.btnPlay.setOnClickListener { onPlayClicked(t) }
    }

    override fun getItemCount(): Int = items.size
}
