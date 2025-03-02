package fr.isen.vincenti.isensmartcompanion.models

data class Course(
    val title : String,
    val room : String,
    val start_time : String,
    val end_time : String,
    val teacher : String,
    val category : String
)