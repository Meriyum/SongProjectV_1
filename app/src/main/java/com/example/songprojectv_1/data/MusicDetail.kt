package com.example.songprojectv_1.data


/////
data class MusicDetail(
    val message: MessageDetail
)




data class MessageDetail(
    val header: HeaderDetail,
    val body: BodyDetail
)

data class HeaderDetail(
    val status_code: Int,
    val execute_time: Double
)

data class BodyDetail(
    val lyrics: Lyrics
)

data class Lyrics(

     val lyrics_body: String,

)
