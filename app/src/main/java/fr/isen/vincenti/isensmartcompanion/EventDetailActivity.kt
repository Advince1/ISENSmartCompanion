package fr.isen.vincenti.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fr.isen.vincenti.isensmartcompanion.composable.EventDetailScreen
import fr.isen.vincenti.isensmartcompanion.models.Event
import fr.isen.vincenti.isensmartcompanion.ui.theme.IsenSmartCompanionTheme

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val event = intent.getSerializableExtra("event") as? Event
        enableEdgeToEdge()
        setContent {
            IsenSmartCompanionTheme {
                EventDetailScreen(
                    eventId = event?.id ?: "Unknown",
                    title = event?.title ?: "No Title",
                    description = event?.description ?: "No Description",
                    date = event?.date ?: "No Date",
                    location = event?.location ?: "No Location",
                    category = event?.category ?: "No Category"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IsenSmartCompanionTheme {
    }
}