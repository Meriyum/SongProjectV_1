

package com.example.songprojectv_1.ui.song

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
//import androidx.compose.ui.tooling.data.EmptyGroup.name
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.songApplication.data.SongsRepository
import com.example.songprojectv_1.data.SongsRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SongEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val songsRepository: SongsRepository
) : ViewModel() {

    /**
     * Holds current song ui state
     */
    var songUiState by mutableStateOf(SongUiState())
        private set

    private val songId: Int = checkNotNull(savedStateHandle[SongEditDestination.songIdArg])
    init {
        viewModelScope.launch {
            songUiState = songsRepository.getSongStream(songId)
                .filterNotNull()
                .first()
                .toSongUiState(true)
        }
    }
    suspend fun updateSong() {
        if (validateInput(songUiState.songDetails)) {
            songsRepository.updateSong(songUiState.songDetails.toItem())
        }
    }

    /**
     * Updates the [itemUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(songDetails: SongDetails) {
        songUiState =
            SongUiState(songDetails = songDetails)
    }

       private fun validateInput(uiState: SongDetails = songUiState.songDetails): Boolean {
        return with(uiState) {
            track_name.isNotBlank() && artist_name.isNotBlank() && lyrics_body.isNotBlank()

        }
    }
}
