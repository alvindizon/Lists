package com.alvindizon.lists.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MyListEntity::class, MyListItemEntity::class], version = 1)
abstract class MyListDatabase : RoomDatabase() {
    abstract fun myListDao(): MyListDao
}
