package com.alvindizon.lists.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.alvindizon.lists.data.room.MyListDao
import com.alvindizon.lists.data.room.MyListDatabase
import com.alvindizon.lists.data.sqldelight.TestDatabase
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

    @Provides
    @Singleton
    fun provideSqlDriver(@ApplicationContext context: Context): SqlDriver {
        return AndroidSqliteDriver(TestDatabase.Schema, context, "my_list.db",
            callback = object : AndroidSqliteDriver.Callback(TestDatabase.Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.setForeignKeyConstraintsEnabled(true)
                }
            })
    }

    @Provides
    @Singleton
    fun provideTestDatabase(sqlDriver: SqlDriver): TestDatabase {
        return TestDatabase(sqlDriver)
    }
}