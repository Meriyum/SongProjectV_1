

package com.example.songprojectv_1.data

import android.content.Context
import com.example.songprojectv_1.api.ApiInterface
import com.example.songprojectv_1.api.ApiUtilities
import com.example.songApplication.data.SongsDatabase


interface AppContainer {

    val songsRepository: SongsRepository

}

        class AppDataContainer(private val context: Context) : AppContainer {
            override val songsRepository: SongsRepository by lazy {
                val apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)
                val songsDao = SongsDatabase.getDatabase(context).songsDao()
                OfflineSongsRepository(apiInterface, songsDao, context)
            }
        }
