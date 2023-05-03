package net.rynkbit.simplechores.ui.chores

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import net.rynkbit.simplechores.MainActivityViewModel
import net.rynkbit.simplechores.database.Chore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditChore(mainViewModel: MainActivityViewModel, choreId: Int?) {
    if (choreId == null) {
        mainViewModel.navController.popBackStack()
    }

    val editChoreViewModel: EditChoreViewModel = viewModel<EditChoreViewModel>().apply {
        choreDao = mainViewModel.database.choreDao()
    }

    val dbChore = editChoreViewModel.choreDao.get(choreId!!).observeAsState()

    dbChore.value?.let { chore ->
        val description = remember {
            mutableStateOf(chore.description)
        }

        val interval = remember {
            mutableStateOf(chore.interval.toString())
        }


        Scaffold(floatingActionButton = {
            SaveDateFab(onClick = {
                editChoreViewModel.updateChore(
                    Chore(
                        uid = chore.uid,
                        description = description.value,
                        interval = interval.value.toInt(),
                        lastCheck = null
                    )
                ) {
                    mainViewModel.navController.popBackStack()
                }
            })
        },
            floatingActionButtonPosition = FabPosition.End,
            topBar = {
                TopAppBar(title = {
                    Text(text = "Change Chore")
                })
            }) {
            ChoreDetails(
                it.calculateTopPadding(),
                description,
                interval
            )
        }
    }
}