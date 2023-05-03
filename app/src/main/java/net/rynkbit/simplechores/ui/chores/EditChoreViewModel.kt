package net.rynkbit.simplechores.ui.chores

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.rynkbit.simplechores.database.Chore
import net.rynkbit.simplechores.database.ChoreDao

class EditChoreViewModel: ViewModel() {
    lateinit var choreDao: ChoreDao

    fun updateChore(chore: Chore, mainThreadCallback: (() -> Unit)?) {
        CoroutineScope(Dispatchers.IO).launch {
            choreDao.update(chore)

            CoroutineScope(Dispatchers.Main).launch {
                mainThreadCallback?.invoke()
            }
        }
    }
}