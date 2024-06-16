package com.alvindizon.lists.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MyListDao {

    @Query("SELECT * FROM my_list")
    fun getAll(): Flow<List<MyListEntity>>

    @Insert
    suspend fun insertAll(vararg lists: MyListEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<MyListEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(list: List<MyListItemEntity>)

    @Query("SELECT * FROM my_list WHERE listId LIKE :listId LIMIT 1")
    suspend fun getMyListById(listId: Int): MyListEntity?

    @Transaction
    @Query("SELECT * FROM my_list WHERE listId LIKE :listId LIMIT 1")
    fun getMyListItemsById(listId: Int): Flow<ListWithItem>

    @Transaction
    suspend fun insertListWithItem(list: List<ListWithItem>) {
        insertList(list.map { it.myList })
        insertItems(list.flatMap { it.items })
    }

}