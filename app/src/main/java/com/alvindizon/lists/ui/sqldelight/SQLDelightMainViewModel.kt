package com.alvindizon.lists.ui.sqldelight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvindizon.lists.data.sqldelight.TestDataSource
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
class SQLDelightMainViewModel @Inject constructor(private val testDataSource: TestDataSource) :
    ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState.Empty)
    val uiState = _uiState.asStateFlow()

    private var number: Long? = null

    init {
        testDataSource.getAllLists()
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
                testDataSource.insertList(
                    listName = name,
                    listId = number!!
                )
                _uiState.update { state ->
                    state.copy(
                        list = state.list?.toMutableList()?.apply {
                            add(
                                MyList(
                                    name = name,
                                    id = number!!,
                                    items = emptyList()
                                )
                            )
                        })
                }
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            testDataSource.deleteList()
        }
    }

    fun deleteList(id: Long) {
        viewModelScope.launch {
            testDataSource.deleteListById(id)
        }
    }
}

fun lists.database.MyListEntity.toUiModel() = MyList(
    name = name,
    id = listId,
    items = emptyList()
)