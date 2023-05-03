package net.rynkbit.simplechores.ui.chores

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import net.rynkbit.simplechores.MainActivityViewModel
import net.rynkbit.simplechores.database.Chore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChore(mainViewModel: MainActivityViewModel) {
    val description = remember {
        mutableStateOf("")
    }

    val interval = remember {
        mutableStateOf("1")
    }

    val addViewModel: AddChoreViewModel = viewModel()

    addViewModel.choreDao = mainViewModel.database.choreDao()

    Scaffold(floatingActionButton = {
        SaveDateFab(onClick = {
            addViewModel.saveChore(Chore(
                description = description.value,
                interval = interval.value.toInt(),
                lastCheck = null
            )) {
                mainViewModel.navController.popBackStack()
            }
        })
    },
        floatingActionButtonPosition = FabPosition.End,
        topBar = {
            TopAppBar(title = {
                Text(text = "Add Chore")
            })
        }) {
        ChoreDetails(
            it.calculateTopPadding(),
            description,
            interval
        )
    }
}
