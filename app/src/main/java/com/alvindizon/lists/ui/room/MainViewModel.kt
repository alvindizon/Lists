package com.alvindizon.lists.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvindizon.lists.data.room.MyListDao
import com.alvindizon.lists.data.room.MyListEntity
import com.alvindizon.lists.model.MyList
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


data class MainUiState(
    val list: List<MyList>? = emptyList()
) {
    companion object {
        val Empty = MainUiState()
    }

}

@HiltViewModel
class MainViewModel @Inject constructor(private val dao: MyListDao) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState.Empty)
    val uiState = _uiState.asStateFlow()

    var number: Long? = null

    init {
        dao.getAll()
            .onEach { list -> _uiState.update { state -> state.copy(list = list.map { it.toUiModel() }) } }
            .launchIn(viewModelScope)

    }

    fun createNewList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val randomGenerator = Random(System.currentTimeMillis())
                if (number == null) {
                    number = randomGenerator.nextLong(0L, 1000L)
                } else {
                    number = number!! + 1
                }

                val name = "MyList $number"
                val list = MyListEntity(listId = number!!, name = name)
                dao.insertAll(list)
                _uiState.update { state ->
                    state.copy(list = state.list?.toMutableList()?.apply { add(list.toUiModel()) })
                }
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            dao.delete()
        }
    }
}

fun MyListEntity.toUiModel() = MyList(
    name = name,
    id = listId,
    items = emptyList()
)