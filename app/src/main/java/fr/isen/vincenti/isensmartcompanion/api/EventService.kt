package fr.isen.vincenti.isensmartcompanion.api

import fr.isen.vincenti.isensmartcompanion.models.Event
import retrofit2.http.GET

interface EventService {
    @GET("events.json")
    suspend fun getEvents(): List<Event>
}

