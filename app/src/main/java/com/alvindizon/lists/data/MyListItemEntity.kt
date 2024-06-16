package com.alvindizon.lists.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class MyListItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val listId: Int,
    val itemName: String
)
