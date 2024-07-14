package com.alvindizon.lists.ui.sqldelight

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvindizon.lists.data.sqldelight.TestDataSource
import com.alvindizon.lists.model.MyListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lists.database.ItemEntity
import javax.inject.Inject
import kotlin.random.Random

data class ListDetailsUiState(
    val items: List<MyListItem>? = emptyList(),
    val listId: Long? = null
) {
    companion object {
        val Empty = ListDetailsUiState()
    }
}

@HiltViewModel
class SQLDelightDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val testDataSource: TestDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListDetailsUiState.Empty)
    val uiState = _uiState.asStateFlow()

    val listId: Long? = savedStateHandle["listId"]

    init {
        _uiState.update { it.copy(listId = listId) }
        _uiState.value.listId?.let { listId ->
            testDataSource.getItemsForList(listId)
                .onEach {
                    _uiState.update { state -> state.copy(items = it.toUiModel()) }
                }
                .launchIn(viewModelScope)
        }
    }

    fun onAddItemClick() {
        _uiState.value.listId?.let { addItem(it) }
    }

    private fun addItem(listId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val randomGenerator = Random(System.currentTimeMillis())
                val result = randomGenerator.nextLong(0, 1000)
                testDataSource.insertItem(itemName =  "Item $result", listId = listId)
            }
        }
    }

    fun onDeleteClick(itemId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                testDataSource.deleteItemById(itemId)
            }
        }
    }
}

fun List<ItemEntity>.toUiModel(): List<MyListItem> = map {
    MyListItem(it.itemName, it.id)
}