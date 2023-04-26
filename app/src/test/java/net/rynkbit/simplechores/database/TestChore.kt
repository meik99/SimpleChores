package net.rynkbit.simplechores.database

import net.rynkbit.simplechores.database.Chore
import org.junit.Test
import org.testng.Assert.assertFalse
import org.testng.Assert.assertTrue
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

class TestChore {
    @Test
    fun isDue() {
        val neverChecked = Chore(0, "test chore", 1, lastCheck = null)

        assertTrue(neverChecked.isDue())

        val beforeNextCheck = Chore(0, "test chore", 1, lastCheck = Date())

        assertFalse(beforeNextCheck.isDue())

        val calendar = GregorianCalendar()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        var afterNextCheck = Chore(0, "test chore", 1, lastCheck = calendar.time)

        assertTrue(afterNextCheck.isDue())

        afterNextCheck = Chore(0, "test chore", 2, lastCheck = calendar.time)

        assertFalse(afterNextCheck.isDue())

        calendar.add(Calendar.DAY_OF_YEAR, -1)
        afterNextCheck = Chore(0, "test chore", 2, lastCheck = calendar.time)

        assertTrue(afterNextCheck.isDue())
    }
}