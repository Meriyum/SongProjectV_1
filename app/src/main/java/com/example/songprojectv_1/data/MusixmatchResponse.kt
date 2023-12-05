package com.example.songprojectv_1.data

data class MusixmatchResponse(
    val message: Message
)

data class Message(
    val header: Header,
    val body: Body
)

data class Header(
    val status_code: Int,
    val execute_time: Double,
    val available: Int
)

data class Body(
    val track_list: List<TrackItem>
)

data class TrackItem(
    val track: Track
)

data class Track(
    val track_id: Int,
    val track_name: String,

    val artist_name: String,

    )


