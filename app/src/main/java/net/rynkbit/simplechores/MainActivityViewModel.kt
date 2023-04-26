package net.rynkbit.simplechores

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import net.rynkbit.simplechores.database.Database

class MainActivityViewModel : ViewModel() {
    lateinit var navController: NavHostController
    lateinit var database: Database
}