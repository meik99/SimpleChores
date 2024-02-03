package net.rynkbit.simplechores.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Drawer(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet() {
                Text("Tasks", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                Divider()
                NavItem(navController, "Current", "chore-overview")
                NavItem(navController, "Scheduled", "chore-scheduled")
                NavItem(navController, "Archived", "chore-archived")
            }
        },
        content = content
    )
}

@Composable
private fun NavItem(navController: NavHostController, text: String = "", activeRoute: String = "") {
    NavigationDrawerItem(
        label = { Text(text = text) },
        selected = navController.currentBackStackEntry?.destination?.route == activeRoute,
        onClick = {
            navController.navigate(activeRoute)
        }
    )
}