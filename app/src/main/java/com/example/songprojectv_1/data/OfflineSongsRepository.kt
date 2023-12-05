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

package com.example.songprojectv_1.data

import android.content.Context
import com.example.songprojectv_1.api.ApiInterface

import kotlinx.coroutines.flow.Flow

class OfflineSongsRepository(
    private val apiInterface: ApiInterface,
    private val songsDao: SongsDao,
    private val applicationContext: Context
) :
    SongsRepository {
    //  override val songsLiveData: MutableLiveData<MusixmatchResponse> = MutableLiveData()
    override fun getAllItemsStream(): Flow<List<Songs>> = songsDao.getAllSongs()

    override fun getSongStream(track_id: Int): Flow<Songs?> = songsDao.getSong(track_id)

    override suspend fun insertSong(songs: Songs) = songsDao.insert(songs)

    override suspend fun deleteSong(songs: Songs) = songsDao.delete(songs)

    override suspend fun updateSong(songs: Songs) = songsDao.update(songs)


    override suspend fun getTrackDetail(
        trackName: String,
        q_lyrics: String,
        apiKey: String

    ) = apiInterface.getTracks(trackName, q_lyrics, apiKey)

    override suspend fun getDetailSongs(Trackid: Int, apiKey: String, ) =
        apiInterface.getTracksDetail(Trackid, apiKey)
}
