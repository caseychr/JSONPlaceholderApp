package com.practice.jsonplaceholderapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.practice.jsonplaceholderapp.model.PostReturn

@Database(entities = [PostReturn::class], version = 1)
abstract class PlaceholderDB : RoomDatabase() {

    /**
     * Set these abstract methods to corresponding Dao instances in their respective repositories
     * @return
     */

    abstract val placeHolderDao: PlaceholderDao

    companion object {

        private const val DATABASE_NAME = "post_db"

        @Volatile
        private lateinit var instance: PlaceholderDB

        fun getInstance(context: Context): PlaceholderDB {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlaceholderDB::class.java, DATABASE_NAME
                ).build()
            }
            return instance
        }
    }

}