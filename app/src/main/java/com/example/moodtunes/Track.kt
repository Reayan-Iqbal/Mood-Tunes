package com.example.moodtunes

data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val resRawName: String, // resource under res/raw e.g., "sample1"
    val mood: String
)
