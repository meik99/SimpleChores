package net.rynkbit.simplechores.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ChoreDao {
    @Query("SELECT * FROM Chore")
    fun getAll(): LiveData<List<Chore>>

    @Query("SELECT * FROM CHORE WHERE uid = :choreId")
    fun get(choreId: Int): LiveData<Chore>

    @Insert
    fun insert(chore: Chore)

    @Delete
    fun delete(chore: Chore)

    @Update
    fun update(chore: Chore)
}