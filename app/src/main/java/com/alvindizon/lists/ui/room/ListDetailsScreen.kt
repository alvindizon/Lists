package com.alvindizon.lists.ui.room

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ListDetailsScreen(viewModel: ListDetailsViewModel = hiltViewModel(), onNavigationIconClick: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Details for List ${state.listId}"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigationIconClick) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::onAddItemClick) {
                Icon(Icons.Filled.Add, "Create list")
            }
        }
    ) { paddingValues ->
        ListDetailsContent(
            modifier = Modifier.padding(paddingValues),
            state = state
        )
    }
}


@Composable
private fun ListDetailsContent(modifier: Modifier = Modifier, state: ListDetailsUiState) {
    Box(modifier = modifier.fillMaxSize()) {
        state.items?.let { list ->
            LazyColumn {
                items(list, key = { it.hashCode() }) { item ->
                    Row(modifier = Modifier.padding(8.dp)) {
                        Text(text = item.name)
                    }
                }
            }
        }
    }
}