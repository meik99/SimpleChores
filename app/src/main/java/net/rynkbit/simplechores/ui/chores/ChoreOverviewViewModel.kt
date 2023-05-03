package net.rynkbit.simplechores.ui.chores

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.rynkbit.simplechores.database.Chore
import net.rynkbit.simplechores.database.ChoreDao
import java.util.Date

class ChoreOverviewViewModel: ViewModel() {
    lateinit var choreDao: ChoreDao
    lateinit var choresState: State<List<Chore>?>

    fun hasChores(): Boolean {
        return choresState.value?.isEmpty() == false
    }

    fun chores(): List<Chore> {
        return choresState.value ?: emptyList()
    }

    fun updateChore(chore: Chore) {
        CoroutineScope(Dispatchers.IO).launch {
            val toUpdate = Chore(chore.uid, chore.description, chore.interval, Date())
            choreDao.update(toUpdate)
        }
    }

    fun deleteChore(chore: Chore) {
        CoroutineScope(Dispatchers.IO).launch {
            choreDao.delete(chore)
        }
    }
}