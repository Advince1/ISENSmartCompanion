package fr.isen.vincenti.isensmartcompanion.composable

import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.isen.vincenti.isensmartcompanion.R
import fr.isen.vincenti.isensmartcompanion.notifications.scheduleNotification

@Composable
fun EventDetailScreen(
    eventId: String,
    title: String,
    description: String,
    date: String,
    location: String,
    category: String
) {
    val activity = LocalActivity.current
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    var isNotified by remember { mutableStateOf(sharedPreferences.getBoolean(eventId, false)) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = colorResource(id = R.color.red_grenat),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(color = Color.Black, thickness = 3.dp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = stringResource(R.string.description) + " $description", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.Black, thickness = 1.dp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(R.string.date) + " $date", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = stringResource(R.string.location) + " $location", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = stringResource(R.string.category) + " $category", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.Black, thickness = 1.dp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { activity?.finish() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.red_grenat),
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.back))
            }

            Button(
                onClick = {
                    isNotified = !isNotified
                    sharedPreferences.edit().putBoolean(eventId, isNotified).apply()

                    if (isNotified) {
                        scheduleNotification(context, title, date, location, category)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isNotified) Color.Gray else colorResource(id = R.color.red_grenat),
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = stringResource(R.string.toggle_notification),
                )
                Text(text = if (isNotified) stringResource(R.string.unotify_me) else stringResource(R.string.notify_me))
            }
        }
    }
}