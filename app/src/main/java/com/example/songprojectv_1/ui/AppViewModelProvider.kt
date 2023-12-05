

package com.example.songprojectv_1.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import com.example.songprojectv_1.ui.song.ItemEntryViewModel
import com.example.songprojectv_1.SongApplication
import com.example.songprojectv_1.ui.home.HomeViewModel
import com.example.songprojectv_1.ui.song.SongDetailsViewModel
import com.example.songprojectv_1.ui.song.SongEditViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            SongEditViewModel(
                this.createSavedStateHandle(),
                songsApplication().container.songsRepository
            )
        }

        initializer {
           ItemEntryViewModel(songsApplication().container.songsRepository)
              }

        // Initializer for ItemDetailsViewModel
        initializer {
            SongDetailsViewModel(
                this.createSavedStateHandle(),
                songsApplication().container.songsRepository
            )

        }

        initializer {
            HomeViewModel(songsApplication().container.songsRepository)

        }
    }



}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [SongApplication].
 */
fun CreationExtras.songsApplication(): SongApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as SongApplication)
