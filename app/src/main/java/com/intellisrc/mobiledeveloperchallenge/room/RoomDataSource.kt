package com.intellisrc.mobiledeveloperchallenge.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.intellisrc.mobiledeveloperchallenge.Constants
import com.intellisrc.mobiledeveloperchallenge.room.dao.RatesDao
import com.intellisrc.mobiledeveloperchallenge.room.entity.RatesEntity

@Database(entities = [
    RatesEntity::class
], version = 1)
abstract class RoomDataSource : RoomDatabase() {
    abstract fun ratesDao(): RatesDao

    companion object {
        fun buildPersistent(context: Context): RoomDataSource = Room.databaseBuilder(
            context.applicationContext,
            RoomDataSource::class.java,
            Constants.DATABASE_NAME
        ).allowMainThreadQueries().build()
    }
}