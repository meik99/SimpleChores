package net.rynkbit.simplechores.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration

@Database(
    entities = [
        Chore::class
    ],
    version = 2
)
@TypeConverters(value = [
    DateConverter::class
])
abstract class Database : RoomDatabase() {
    abstract fun choreDao(): ChoreDao
}