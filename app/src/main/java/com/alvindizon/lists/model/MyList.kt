package com.alvindizon.lists.model

data class MyList(
    val name: String,
    val id: Int,
    val items: List<MyListItem>
)

data class MyListItem(
    val name: String
)
