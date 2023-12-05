package com.example.songprojectv_1.api


import com.example.songprojectv_1.data.MusixmatchResponse
import com.example.songprojectv_1.data.MusicDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("track.search")
    suspend fun getTracks(@Query("q_track") trackName: String,@Query("q_lyrics") q_lyrics: String, @Query("apikey") apiKey: String): Response<MusixmatchResponse>
    @GET("track.lyrics.get")
    suspend fun getTracksDetail(@Query("track_id") track_id: Int, @Query("apikey") apiKey: String): Response<MusicDetail>


}