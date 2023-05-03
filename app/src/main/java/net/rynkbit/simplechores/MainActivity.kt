package net.rynkbit.simplechores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import net.rynkbit.simplechores.database.Database
import net.rynkbit.simplechores.ui.chores.AddChore
import net.rynkbit.simplechores.ui.chores.ChoreOverview
import net.rynkbit.simplechores.ui.chores.EditChore
import net.rynkbit.simplechores.ui.theme.SimpleChoresTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room
            .databaseBuilder(
                applicationContext,
                Database::class.java, "database"
            )
            .fallbackToDestructiveMigration()
            .build()

        setContent {
            SimpleChoresTheme {
                val navController = rememberNavController()
                val viewModel: MainActivityViewModel = viewModel()

                viewModel.database = database
                viewModel.navController = navController

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "chore-overview") {
                        composable(getString(R.string.nav_chore_overview)) {
                            ChoreOverview(viewModel)
                        }
                        composable(getString(R.string.nav_chore_add)) {
                            AddChore(viewModel)
                        }
                        composable("chore-edit/{chore-id}",
                            listOf(navArgument("chore-id") { type = NavType.IntType })
                        ) {
                            EditChore(viewModel, it.arguments?.getInt("chore-id"))
                        }
                    }
                }
            }
        }
    }
}
