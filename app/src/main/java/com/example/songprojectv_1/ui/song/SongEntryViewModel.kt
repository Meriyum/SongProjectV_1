/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.songprojectv_1.ui.song

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.songprojectv_1.data.MusixmatchResponse
import com.example.songprojectv_1.data.MusicDetail
import com.example.songprojectv_1.data.Songs
import com.example.songprojectv_1.data.SongsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ItemEntryViewModel(private val songsRepository: SongsRepository) : ViewModel() {

    var songUiState by mutableStateOf(SongUiState())
        private set


    fun updateUiState(songDetails: SongDetails) {
        songUiState =
            SongUiState(songDetails = songDetails, isEntryValid = validateInput(songDetails))
    }



    private fun validateInput(uiState: SongDetails = songUiState.songDetails): Boolean {
        return with(uiState) {
            track_name.isNotBlank() && artist_name.isNotBlank() && lyrics_body.isNotBlank()
        }
    }
    val API_KEY = "559f7e68ee59459369e4403ef24fd978"
    suspend fun searchSong(trackName: MutableState<String>, q_lyrics: MutableState<String>) {

        //    val songsLiveData= MutableLiveData<MusixmatchResponse>()
        Log.d("tname", "trgid$${trackName}")
        val trackName: String = trackName.value
        val q_lyrics: String = q_lyrics.value

        viewModelScope.launch {
            val result = songsRepository.getTrackDetail(trackName, q_lyrics, API_KEY)
            _songsLiveData.postValue(result.body())
        }

    }

    fun loadSongsDetail(Trackid: Int, titledetail: String, authordetail: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val result =songsRepository.getDetailSongs(Trackid,API_KEY)
            if(result.body()!=null){
            songsLiveDataDetail.postValue(result.body())
            //////////////////////////////////////

                val songDetailResult = result.body()
                val lyricsBody = songDetailResult?.message?.body?.lyrics?.lyrics_body ?: ""

                // Create SongDetail object
                val Songs = Songs(
                    track_id = Trackid,
                    track_name = titledetail,
                    artist_name = authordetail,
                    lyrics_body =lyricsBody// Replace this with the actual property from songDetailResult
                )
                try {
                    songsRepository.insertSong(Songs)
                } catch (e: Exception) {
                    Log.e("DatabaseInsertError", "Error inserting data into the database", e)
                }

                // Optionally, you can also post the result to LiveData
                //  songsLiveDataDetail.postValue(songDetailResult)
            }


            ///////////////////////////////////////////
        }
    }

    private val _songsLiveData = MutableLiveData<MusixmatchResponse>()
    private val songsLiveDataDetail=MutableLiveData<MusicDetail>()
    val song: LiveData<MusixmatchResponse>
        get() = _songsLiveData


    }



data class HomeUiState(val songsList: List<Songs> = listOf())




//////////////////////////////////
data class SongUiState(
    val songDetails: SongDetails = SongDetails(),
    val isEntryValid: Boolean = true
)
data class Songs(
    val track_id: Int = 0,
    val track_name: String = "",
    val artist_name: String = "",
    val lyrics_body: String = "",
)
data class SongDetails(
    val track_id: Int = 0,
    val track_name: String = "",
    val artist_name: String = "",
    val lyrics_body: String = "",
)



data class TrackDetails(
    val track_id: Int = 0,
    val track_name: String = "",
    val artist_name: String = "",

)


fun SongDetails.toItem(): Songs = Songs(
    track_id = track_id,
    track_name = track_name,
    artist_name = artist_name,
    lyrics_body = lyrics_body

)

fun Songs.toSongUiState(isEntryValid: Boolean = true): SongUiState = SongUiState(
    songDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

fun Songs.toItemDetails(): SongDetails = SongDetails(
    track_id = track_id,
    track_name = track_name,
    artist_name = artist_name,
    lyrics_body = lyrics_body
)
