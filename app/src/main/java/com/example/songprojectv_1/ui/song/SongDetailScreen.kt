package com.example.songprojectv_1.ui.song

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.songprojectv_1.ui.navigation.NavigationDestination
import com.example.songprojectv_1.R
import com.example.songprojectv_1.SongsTopAppBar

import com.example.songprojectv_1.ui.AppViewModelProvider
import kotlinx.coroutines.launch


object SongDetailsDestination : NavigationDestination {
    override val route = "item_details"
    override val titleRes = R.string.song_detail_title
    const val songIdArg = "track_id"
    val routeWithArgs = "$route/{$songIdArg}"
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongDetailsScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SongDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)

){
//
val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
Scaffold(

topBar = {
    SongsTopAppBar(
        title = stringResource(SongDetailsDestination.titleRes),
        canNavigateBack = true,
        navigateUp = navigateBack
    )
},  )
{ innerPadding ->
   ItemDetailBody(
        songsItem = uiState.songDetails,
       onDeleteClick = {
           coroutineScope.launch {
               viewModel.deleteSong()
               navigateBack()
           }
       },
       onEditClick = {

           navigateToEditItem(uiState.songDetails.track_id)

       },
       onPlayClick = {
           coroutineScope.launch {

               navigateBack()
           }},

       modifier = Modifier
           .padding(innerPadding)
            .fillMaxSize()

    )
}}
  @Composable
   fun ItemDetailBody(songsItem: SongDetails, modifier: Modifier,onDeleteClick: () -> Unit,onEditClick: () -> Unit,onPlayClick: () -> Unit) {
       Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Track ID: ${songsItem.track_id}",
                        style = TextStyle(fontSize = 20.sp, color = Color.Black)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = "Track Name: ${songsItem.track_name}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                Text(
                    text = "artist name: ${songsItem.artist_name}",
                    style = MaterialTheme.typography.titleMedium
                )}
                var isExpanded by remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){

                    Text(
                    text = "lyrics: ${songsItem.lyrics_body}",
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                    modifier = Modifier.clickable { isExpanded = !isExpanded }
                )
            }

                Spacer(modifier = Modifier.height(25.dp))
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)

                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color(0xffa8d1df))
                            .clickable {
                               onDeleteClick()

                            },

                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete),
                        )}
                        ////////////////////////////////////////////////////////
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(Color(0xffa8d1df))
                                .clickable {
                                    onEditClick()

                                },

                                    contentAlignment = Alignment.Center
                        ){
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = stringResource(R.string.delete),
                            )

                    }
                /////////////////////////
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color(0xffa8d1df))
                        .clickable {
                            //onMQTTClick()

                        },

                    contentAlignment = Alignment.Center
                ){
                    Text(text = "MQTT",
                        style = typography.titleLarge

                    )


                }}}

     }
    }
