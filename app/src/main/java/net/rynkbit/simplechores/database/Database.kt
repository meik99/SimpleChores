package net.rynkbit.simplechores.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        Chore::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(value = [
    DateConverter::class
])
abstract class Database : RoomDatabase() {
    abstract fun choreDao(): ChoreDao
}