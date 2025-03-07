package fr.isen.vincenti.isensmartcompanion.composable

import android.content.Context
import android.widget.CalendarView
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import fr.isen.vincenti.isensmartcompanion.R
import fr.isen.vincenti.isensmartcompanion.models.Event
import fr.isen.vincenti.isensmartcompanion.api.RetrofitInstance
import fr.isen.vincenti.isensmartcompanion.utils.getCoursesForDate
import fr.isen.vincenti.isensmartcompanion.models.Course
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AgendaScreen() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    var selectedDate by remember { mutableStateOf("Sélectionner une date") }
    var selectedDatebis by remember { mutableStateOf("") }
    var coursesList by remember { mutableStateOf<List<Course>>(emptyList()) }
    var eventList by remember { mutableStateOf<List<Event>>(emptyList()) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val events = RetrofitInstance.eventService.getEvents()
                eventList = events
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.agenda),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        AndroidView(
            factory = { ctx ->
                CalendarView(ctx).apply {
                    setOnDateChangeListener { _, year, month, dayOfMonth ->
                        val calendar = Calendar.getInstance()
                        calendar.set(year, month, dayOfMonth)

                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("fr", "FR"))
                        selectedDate = dateFormat.format(calendar.time)

                        val dateFormatbis = SimpleDateFormat("dd MMMM yyyy", Locale("fr", "FR"))
                        selectedDatebis = dateFormatbis.format(calendar.time)
                        coursesList = getCoursesForDate(selectedDate, context)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text(
            text = selectedDatebis,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 4.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            if (coursesList.isNotEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.courses),
                            style = MaterialTheme.typography.bodyLarge,
                            color = colorResource(id = R.color.red_grenat),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            items(coursesList) { course ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.red_grenat))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = course.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        HorizontalDivider(color = Color.White, thickness = 1.dp)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = stringResource(R.string.room) + " ${course.room}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Text(
                            text = stringResource(R.string.teacher) + " ${course.teacher}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Text(
                            text = stringResource(R.string.start_time) + " ${course.start_time}" + " - " + stringResource(R.string.end_time) + " ${course.end_time}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Text(
                            text = stringResource(R.string.category) + " ${course.category}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
            }

            eventList.filter { event -> event.date == selectedDatebis && sharedPreferences.getBoolean(event.id, false) }.forEach { event ->
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.notified_event),
                            style = MaterialTheme.typography.bodyLarge,
                            color = colorResource(id = R.color.red_grenat),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 8.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.red_grenat))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(R.string.event) + " ${event.title}",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            HorizontalDivider(color = Color.White, thickness = 1.dp)
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(text = stringResource(R.string.date) + " ${event.date}", color = Color.White)
                            Text(text = stringResource(R.string.location) + " ${event.location}", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
