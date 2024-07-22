package com.alvindizon.lists.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MyListItemDao {

    @Insert
    suspend fun insert(listItem: MyListItemEntity)

    @Insert
    suspend fun insertItems(listItems: List<MyListItemEntity>)

    @Query("SELECT item.* FROM item INNER JOIN my_list ON my_list.listId = item.listId WHERE item.listId = :listId")
    fun getItemsForList(listId: Long): Flow<List<MyListItemEntity>>

    @Query("SELECT * FROM item WHERE listId = :listId")
    fun getItemsForList2(listId: Long): Flow<List<MyListItemEntity>>

    @Query("DELETE FROM item WHERE id = :id")
    suspend fun deleteListItem(id: Long)
}