package fr.isen.vincenti.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
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
                    eventId = event?.id ?: stringResource(R.string.unknown),
                    title = event?.title ?: stringResource(R.string.no_title),
                    description = event?.description ?: stringResource(R.string.no_description),
                    date = event?.date ?: stringResource(R.string.no_date),
                    location = event?.location ?: stringResource(R.string.no_location),
                    category = event?.category ?: stringResource(R.string.no_category)
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