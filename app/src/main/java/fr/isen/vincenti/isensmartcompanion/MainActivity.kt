package fr.isen.vincenti.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import fr.isen.vincenti.isensmartcompanion.ui.theme.IsenSmartCompanionTheme
import fr.isen.vincenti.isensmartcompanion.composable.BottomNavigationBar
import fr.isen.vincenti.isensmartcompanion.composable.NavigationGraph
import fr.isen.vincenti.isensmartcompanion.notifications.createNotificationChannel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel(this)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            IsenSmartCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavigationGraph(navController)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    IsenSmartCompanionTheme {
    }
}
