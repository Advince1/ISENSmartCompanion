package fr.isen.vincenti.isensmartcompanion.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.isen.vincenti.isensmartcompanion.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.home)) },
            label = { Text(stringResource(R.string.home)) },
            selected = navController.currentDestination?.route == "home",
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = stringResource(R.string.events)) },
            label = { Text(stringResource(R.string.events)) },
            selected = navController.currentDestination?.route == "events",
            onClick = { navController.navigate("events") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = stringResource(R.string.history)) },
            label = { Text(stringResource(R.string.history)) },
            selected = navController.currentDestination?.route == "history",
            onClick = { navController.navigate("history") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.DateRange , contentDescription = stringResource(R.string.agenda)) },
            label = { Text(stringResource(R.string.agenda)) },
            selected = navController.currentDestination?.route == "agenda",
            onClick = { navController.navigate("agenda") }
        )
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { MainScreen() }
        composable("events") { EventsScreen() }
        composable("history") { HistoryScreen() }
        composable("agenda") { AgendaScreen() }
    }
}