package net.rynkbit.simplechores.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

@Entity
data class Chore(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "interval") val interval: Int,
    @ColumnInfo(name = "last_check") val lastCheck: Date?
) {
    fun isDue(): Boolean {
        if (lastCheck == null) {
            return true
        }

        if (interval == 0) {
            return false
        }

        val calendar = GregorianCalendar()

        calendar.set(Calendar.HOUR, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        calendar.add(Calendar.DAY_OF_YEAR, -interval)

        return lastCheck.before(calendar.time)
    }
}

