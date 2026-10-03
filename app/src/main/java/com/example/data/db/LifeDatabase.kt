package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PastLifeEntity::class, SavedLifeEntity::class],
    version = 2,
    exportSchema = false
)
abstract class LifeDatabase : RoomDatabase() {
    abstract fun pastLifeDao(): PastLifeDao
    abstract fun savedLifeDao(): SavedLifeDao

    companion object {
        @Volatile
        private var INSTANCE: LifeDatabase? = null

        fun getDatabase(context: Context): LifeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LifeDatabase::class.java,
                    "lifesim_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
