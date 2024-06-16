package com.alvindizon.lists.di

import android.content.Context
import androidx.room.Room
import com.alvindizon.lists.data.MyListDao
import com.alvindizon.lists.data.MyListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMyListDatabase(@ApplicationContext context: Context): MyListDatabase {
        return Room.databaseBuilder(context, MyListDatabase::class.java, "my_list").build()
    }

    @Provides
    @Singleton
    fun myListDao(database: MyListDatabase): MyListDao = database.myListDao()
}