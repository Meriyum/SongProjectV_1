package com.example.songprojectv_1.ui.song

import android.util.Log

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.songprojectv_1.data.MusixmatchResponse
import com.example.songprojectv_1.data.Track

import com.example.songprojectv_1.ui.navigation.NavigationDestination

import com.example.songprojectv_1.R
import com.example.songprojectv_1.SongsTopAppBar
import com.example.songprojectv_1.ui.AppViewModelProvider
import kotlinx.coroutines.launch


object SongEntryDestination : NavigationDestination {
    override val route = "item_entry"
    override val titleRes = R.string.song_entry_title
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ItemEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    viewModel: ItemEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)

) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()
    val trackName = remember { mutableStateOf("") }
    val q_lyrics = remember { mutableStateOf("") }
    // val songState by viewModel.song.observeAsState()


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SongsTopAppBar(
                title = stringResource(SongEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "*",
                        style = TextStyle(
                            color = Color.Red,
                            fontSize = 24.sp
                        ),
                        modifier = Modifier.padding(start = 18.dp)
                    )
                    TextField(
                        value = trackName.value,
                        onValueChange = { trackName.value = it },
                        label = { Text("Enter Song Name") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),


                        trailingIcon = {
                            IconButton(onClick = {
                                trackName.value = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = ""
                                )
                            }
                        }
                    )
                    Text(
                        text = "*",
                        style = TextStyle(
                            color = Color.Red,
                            fontSize = 24.sp
                        ),
                        modifier = Modifier.padding(start = 18.dp)
                    )
                    TextField(
                        value = q_lyrics.value,
                        onValueChange = { q_lyrics.value = it },
                        label = { Text("Enter Lyrics") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        shape = RoundedCornerShape(16.dp),
                        trailingIcon = {
                            IconButton(onClick = {
                                q_lyrics.value = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = ""
                                )
                            }
                        }
                    )
                    Text(
                        text = "Required Fields",
                        style = TextStyle(
                            color = Color.Red,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier.padding(start = 18.dp)
                    )
                    val controller =
                        LocalSoftwareKeyboardController.current
                    Button(

                        onClick = {
                            coroutineScope.launch {
                                controller?.hide()
                                viewModel.searchSong(trackName, q_lyrics)


                            }
                        },
                        modifier = Modifier

                            .padding(vertical = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Search")
                    }
                }
            }

            val songState = viewModel.song.observeAsState()

            if (songState != null) {
                SongsList(songState, viewModel)
            } else {
                Text(
                    text = "No match Found",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier.padding(start = 18.dp)
                )
            }
        }


    }
}


@Composable
fun SongsList(songState: State<MusixmatchResponse?>, viewModel: ItemEntryViewModel) {
    //Text(text = "text")
    songState.value?.let { mismatchResponse ->

        if (mismatchResponse != null) {
            mismatchResponse.message.body.track_list.forEach { trackItem ->
                Log.d(
                    "test1",
                    "name=${trackItem.track.artist_name}, track_id=${trackItem.track.track_id}"
                )
                TrackItemshow(trackItem.track, viewModel)
            }
        }
    }

}


///////////////////////////////////////////

@Composable
fun TrackItemshow(track: Track, viewModel: ItemEntryViewModel) {
    var Trackid by remember { mutableStateOf(0) }
    var titledetail by remember { mutableStateOf("") }
    var authordetail by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier

            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                modifier = Modifier.padding(end = 16.dp)
            )
            Column {
                Text(text = "Name: ${track.artist_name}", style = TextStyle(fontSize = 18.sp))
                Text(
                    text = "Track ID: ${track.track_id}",
                    style = TextStyle(fontSize = 16.sp, color = Color.Gray)
                )
            }
        }
    }
    LaunchedEffect(isChecked) {
        if (isChecked) {
            android.util.Log.d("checkbox", "Checked Track ID: ${track.track_id}")
            Trackid = track.track_id
            titledetail = track.track_name
            authordetail = track.artist_name


            viewModel.loadSongsDetail(Trackid, titledetail, authordetail)
        }
    }
}
