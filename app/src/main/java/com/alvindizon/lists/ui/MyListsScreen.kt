package com.alvindizon.lists.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alvindizon.lists.MainViewModel


@Composable
fun MyListsScreen(
    viewModel: MainViewModel = hiltViewModel(), onListClick: (Long) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(onClick = viewModel::createNewList) {
            Icon(Icons.Filled.Add, "Create list")
        }
    }) { innerPadding ->
        Box(modifier = androidx.compose.ui.Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            state.list?.let { list ->
                LazyColumn {
                    items(list, key = { it.hashCode() }) { item ->
                        Row(modifier = Modifier.padding(8.dp).clickable { onListClick(item.id) }) {
                            Text(text = item.name)
                        }
                    }
                }
            }
        }
    }
}
