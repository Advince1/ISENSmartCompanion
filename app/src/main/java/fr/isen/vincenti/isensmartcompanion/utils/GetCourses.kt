package fr.isen.vincenti.isensmartcompanion.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.isen.vincenti.isensmartcompanion.models.Course
import java.io.InputStreamReader


fun getCoursesForDate(selectedDate: String, context: Context): List<Course> {
    // Accéder au fichier JSON dans raw
    val inputStream = context.resources.openRawResource(
        context.resources.getIdentifier("courses", "raw", context.packageName)
    )
    val reader = InputStreamReader(inputStream)

    // Utiliser Gson pour analyser le fichier JSON
    val gson = Gson()
    val courseType = object : TypeToken<Map<String, Any>>() {}.type
    val jsonData = gson.fromJson<Map<String, Any>>(reader, courseType)

    // Liste des cours
    val coursesList = mutableListOf<Course>()

    // Récupérer la liste des cours dans le JSON
    val courses = jsonData["courses"] as? List<Map<String, Any>> ?: return coursesList

    // Parcourir les cours et récupérer ceux correspondant à la date exacte
    for (course in courses) {

        val courseDate = course["date"] as? String ?: continue  // S'assurer que "date" existe

        // Comparer la date du cours avec la date sélectionnée
        if (courseDate == selectedDate) {
            val title = course["title"] as? String ?: ""
            val room = course["room"] as? String ?: ""
            val startTime = course["start_time"] as? String ?: ""
            val endTime = course["end_time"] as? String ?: ""
            val teacher = course["teacher"] as? String ?: ""
            val category = course["category"] as? String ?: ""

            // Ajouter le cours à la liste si les dates correspondent
            coursesList.add(Course(title, room, startTime, endTime, teacher, category))
        }
    }

    return coursesList
}

