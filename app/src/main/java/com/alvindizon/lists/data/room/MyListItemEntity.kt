package com.alvindizon.lists.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//@Entity(tableName = "item")
@Entity(
    tableName = "item",
    foreignKeys = [ForeignKey(
        entity = MyListEntity::class,
        parentColumns = arrayOf("listId"),
        childColumns = arrayOf("listId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class MyListItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(index = true)
    val listId: Long,
    val itemName: String
)
