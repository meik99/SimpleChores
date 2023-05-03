package net.rynkbit.simplechores.ui.chores

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.rynkbit.simplechores.database.Chore
import net.rynkbit.simplechores.database.ChoreDao

class AddChoreViewModel : ViewModel() {
    lateinit var choreDao: ChoreDao

    fun saveChore(chore: Chore, mainThreadCallback: (() -> Unit)?) {
        CoroutineScope(Dispatchers.IO).launch {
            choreDao.insert(
                chore
            )

            CoroutineScope(Dispatchers.Main).launch {
                mainThreadCallback?.invoke()
            }
        }
    }


}