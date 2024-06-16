package com.alvindizon.lists.data

import androidx.room.Embedded
import androidx.room.Relation

data class ListWithItem(
    @Embedded val myList: MyListEntity,
    @Relation(
        parentColumn = "listId",
        entityColumn = "listId"
    )
    val items: List<MyListItemEntity>
)
