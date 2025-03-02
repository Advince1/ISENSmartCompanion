package fr.isen.vincenti.isensmartcompanion.composable

import android.content.Context
import android.widget.CalendarView
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import fr.isen.vincenti.isensmartcompanion.models.Event
import fr.isen.vincenti.isensmartcompanion.api.RetrofitInstance
import fr.isen.vincenti.isensmartcompanion.utils.getCoursesForDate
import fr.isen.vincenti.isensmartcompanion.models.Course
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun CalendarScreen() {
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
            text = "Agenda",
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
                            text = "Cours",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF800020),
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
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF800020))
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
                            text = "Salle : ${course.room}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Text(
                            text = "Professeur : ${course.teacher}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Text(
                            text = "Début : ${course.start_time} - Fin : ${course.end_time}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Text(
                            text = "Catégorie : ${course.category}",
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
                            text = "Notified Event",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF800020),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 8.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF800020))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Event: ${event.title}",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            HorizontalDivider(color = Color.White, thickness = 1.dp)
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(text = "Date: ${event.date}", color = Color.White)
                            Text(text = "Location: ${event.location}", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
