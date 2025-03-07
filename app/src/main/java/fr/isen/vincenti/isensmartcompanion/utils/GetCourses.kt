package fr.isen.vincenti.isensmartcompanion.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.isen.vincenti.isensmartcompanion.models.Course
import java.io.InputStreamReader


fun getCoursesForDate(selectedDate: String, context: Context): List<Course> {
    val inputStream = context.resources.openRawResource(
        context.resources.getIdentifier("courses", "raw", context.packageName)
    )
    val reader = InputStreamReader(inputStream)

    val gson = Gson()
    val courseType = object : TypeToken<Map<String, Any>>() {}.type
    val jsonData = gson.fromJson<Map<String, Any>>(reader, courseType)

    val coursesList = mutableListOf<Course>()

    val courses = jsonData["courses"] as? List<Map<String, Any>> ?: return coursesList

    for (course in courses) {

        val courseDate = course["date"] as? String ?: continue

        if (courseDate == selectedDate) {
            val title = course["title"] as? String ?: ""
            val room = course["room"] as? String ?: ""
            val startTime = course["start_time"] as? String ?: ""
            val endTime = course["end_time"] as? String ?: ""
            val teacher = course["teacher"] as? String ?: ""
            val category = course["category"] as? String ?: ""

            coursesList.add(Course(title, room, startTime, endTime, teacher, category))
        }
    }

    return coursesList
}

