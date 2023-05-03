package net.rynkbit.simplechores.ui.chores

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.rynkbit.simplechores.MainActivityViewModel
import net.rynkbit.simplechores.R
import net.rynkbit.simplechores.database.Chore
import net.rynkbit.simplechores.ui.theme.Green300
import net.rynkbit.simplechores.ui.theme.Red300
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoreOverview(mainViewModel: MainActivityViewModel) {
    val context = LocalContext.current
    val overviewViewModel: ChoreOverviewViewModel = viewModel()

    overviewViewModel.choreDao = mainViewModel.database.choreDao()
    overviewViewModel.choresState = mainViewModel.database.choreDao().getAll().observeAsState()

    Scaffold(
        floatingActionButton = { AddDateFab { mainViewModel.navController.navigate(context.getString(
            R.string.nav_chore_add
        )) } },
        floatingActionButtonPosition = FabPosition.End,
        topBar = {
            TopAppBar(title = { Text(text = "Chores") })
        }
    ) {
        if (!overviewViewModel.hasChores()) {
            Placeholder()
        } else {
            ChoreList(
                it.calculateTopPadding(),
                overviewViewModel.chores(),
                onChecked = overviewViewModel::updateChore,
                onDelete = overviewViewModel::deleteChore,
                onClick = {chore -> mainViewModel.navController.navigate("chore-edit/${chore.uid}") })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
private fun ChoreList(
    paddingTop: Dp = Dp(8f),
    chores: List<Chore> = generateChores(),
    onChecked: (chore: Chore) -> Unit = {},
    onDelete: (chore: Chore) -> Unit = {},
    onClick: (chore: Chore) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingTop, start = Dp(8f), bottom = Dp(8f), end = Dp(8f))
    ) {
        for (chore in chores) {
            key(chore.uid) {
                if (chore.isDue()) {
                    ChoreCard(chore,
                        onChecked = { onChecked(chore) },
                        onDelete = { onDelete(chore) },
                        onClick = { onClick(chore) })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun ChoreCard(
    chore: Chore = Chore(description = "Test chore", interval = 1, lastCheck = Date()),
    onChecked: () -> Unit = {},
    onDelete: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    val dismissState = rememberDismissState()

    if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
        onChecked()
    } else if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        onDelete()
    }

    SwipeToDismiss(
        state = dismissState,
        background = {
            if (dismissState.dismissDirection == DismissDirection.StartToEnd) {
                DoneCardBackground()
            } else if (dismissState.dismissDirection == DismissDirection.EndToStart) {
                DeleteCardBackground()
            }
        },
        dismissThresholds = {
            FractionalThreshold(0.7f)
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dp(2f))
                .clickable {
                   onClick()
                },
            shape = CardDefaults.outlinedShape,
        ) {
            Text(
                modifier = Modifier
                    .padding(Dp(16f)),
                text = chore.description,
            )
        }
    }
}

@Preview
@Composable
fun DoneCardBackground() {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dp(4f)),
        colors = CardDefaults.cardColors(containerColor = Green300)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dp(4f)),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.Done, contentDescription = "", tint = Color.White)
            Text(text = "Mark as done", color = Color.White)
        }
    }
}

@Preview
@Composable
fun DeleteCardBackground() {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dp(4f)),
        colors = CardDefaults.cardColors(containerColor = Red300)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dp(4f)),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.Clear, contentDescription = "", tint = Color.White)
            Text(text = "Delete chore", color = Color.White)
        }
    }
}

fun generateChores(): List<Chore> =
    List(10, init = {
        Chore(it, "Chore #${it}", interval = it, lastCheck = null)
    })

@Composable
@Preview
private fun Placeholder() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Your chores will show up here",
            )
        }
    }
}

@Composable
@Preview
private fun AddDateFab(onClick: () -> Unit = {}) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Icon(Icons.Filled.Add, "")
    }
}