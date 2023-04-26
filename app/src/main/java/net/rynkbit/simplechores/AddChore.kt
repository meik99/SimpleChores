package net.rynkbit.simplechores

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import net.rynkbit.simplechores.database.Chore
import net.rynkbit.simplechores.database.Database

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun AddChore(navController: NavController = rememberNavController(), database: Database? = null) {
    val description = remember {
        mutableStateOf("")
    }

    val interval = remember {
        mutableStateOf("1")
    }

    Scaffold(floatingActionButton = {
        SaveDateFab(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                database?.apply {
                    choreDao().insert(
                        Chore(
                            description = description.value,
                            interval = interval.value.toInt(),
                            lastCheck = null
                        )
                    )
                }

                CoroutineScope(Dispatchers.Main).launch {
                    navController.popBackStack()
                }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ChoreDetails(
    topPadding: Dp = Dp(8f),
    description: MutableState<String> = mutableStateOf(""),
    interval: MutableState<String> = mutableStateOf("1")

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = topPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dp(8f)),
            value = description.value,
            onValueChange = {
                description.value = it
            },
            label = {
                Text(text = "Description")
            },
            singleLine = true
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dp(8f)),
            value = interval.value,
            onValueChange = {
                val input = it.toIntOrNull()

                if (it.isEmpty()) {
                    interval.value = ""
                } else {
                    interval.value = input?.toString() ?: interval.value
                }
            },
            singleLine = true,
            leadingIcon = { Text(modifier = Modifier.padding(Dp(8f)), text = "Repeat every") },
            trailingIcon = { Text("day(s)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

    }
}

@Composable
@Preview
fun SaveDateFab(onClick: () -> Unit = {}) {
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Filled.Check, "")
    }
}