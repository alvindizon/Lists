package com.alvindizon.lists.ui.room

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvindizon.lists.data.room.MyListItemDao
import com.alvindizon.lists.data.room.MyListItemEntity
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
class ListDetailsViewModel @Inject constructor(savedStateHandle: SavedStateHandle, private val myListItemDao: MyListItemDao) : ViewModel() {

    private val _uiState = MutableStateFlow(ListDetailsUiState.Empty)
    val uiState = _uiState.asStateFlow()

    val listId: Long? = savedStateHandle["listId"]


    init {
        _uiState.update { it.copy(listId = listId) }
        _uiState.value.listId?.let { listId ->
            myListItemDao.getItemsForList2(listId)
                .onEach { list ->
                    _uiState.update { state -> state.copy(items = list.map { it.toUiModel() }) }
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
                val items = listOf(
                    MyListItemEntity(listId = listId, itemName = "Item $result")
                )
                myListItemDao.insertItems(items)
            }
        }
    }
}

fun MyListItemEntity.toUiModel() = MyListItem(itemName, id.toLong())