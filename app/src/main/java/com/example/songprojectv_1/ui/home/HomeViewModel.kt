

package com.example.songprojectv_1.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.songprojectv_1.data.Songs
import com.example.songprojectv_1.data.SongsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(songsRepository: SongsRepository) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        songsRepository.getAllItemsStream().map { HomeUiState(it) }
            .stateIn(

                scope = viewModelScope,

                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),

                initialValue = HomeUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val songsList: List<Songs> = listOf())
