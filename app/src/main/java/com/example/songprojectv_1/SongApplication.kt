

package com.example.songprojectv_1

import android.app.Application

import com.example.songprojectv_1.data.AppContainer
import com.example.songprojectv_1.data.AppDataContainer

class SongApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = AppDataContainer(this)
    }
}
