package com.alvindizon.lists.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "my_list", indices = [Index(value = ["listId"], unique = true)])
data class MyListEntity(
    @PrimaryKey
    val listId: Long,
    val name: String
)
