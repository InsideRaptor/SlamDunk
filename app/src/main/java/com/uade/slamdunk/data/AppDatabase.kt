package com.uade.slamdunk.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uade.slamdunk.model.PlayerDAO
import com.uade.slamdunk.model.PlayerLocal

@Database(entities = [PlayerLocal::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDAO

    companion object {
        @Volatile //Multithreading, se puede acceder desde multiples hilos de ejecución
        private var _instance : AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = _instance ?: synchronized(this) {
            _instance ?: buildDatabase(context).also { _instance = it }
        }

        private fun buildDatabase(context: Context) : AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "nba_db")
            .fallbackToDestructiveMigration()
            .build()
    }
}