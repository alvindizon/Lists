package com.alvindizon.lists.data.sqldelight

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import lists.database.ItemEntity
import lists.database.MyListEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestDataSource @Inject constructor(private val testDatabase: TestDatabase) {

    fun getAllLists() = testDatabase.listQueries.getAllLists().asFlow().mapToList(Dispatchers.IO)

    suspend fun insertList(listName: String, listId: Long) {
        withContext(Dispatchers.IO) {
            testDatabase.listQueries.insert(listId, listName)
        }
    }

    fun getMyListById(listId: Long): Flow<List<MyListEntity>> =
        testDatabase.listQueries.getMyListById(listId).asFlow().mapToList(Dispatchers.IO)

    suspend fun deleteList() {
        withContext(Dispatchers.IO) {
            testDatabase.listQueries.delete()
        }
    }

    suspend fun insertItem(itemName: String, listId: Long) {
        withContext(Dispatchers.IO) {
            testDatabase.itemQueries.insert(null, listId, itemName)
        }
    }

    fun getItemsForList(listId: Long): Flow<List<ItemEntity>> =
        testDatabase.itemQueries.getItemsForList(listId).asFlow().mapToList(Dispatchers.IO)

}