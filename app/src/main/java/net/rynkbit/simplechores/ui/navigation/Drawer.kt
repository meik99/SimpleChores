package net.rynkbit.simplechores.ui.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable

@Composable
fun Drawer(drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed), content: @Composable () -> Unit) {

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet() {

            }
        },
        content = content
    )
}