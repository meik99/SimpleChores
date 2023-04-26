package net.rynkbit.simplechores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import net.rynkbit.simplechores.database.Database
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "chore-overview") {
                        composable(getString(R.string.nav_chore_overview)) {
                            ChoreOverview(navController, database)
                        }
                        composable(getString(R.string.nav_chore_add)) {
                            AddChore(navController, database)
                        }
                    }
                }
            }
        }
    }
}
