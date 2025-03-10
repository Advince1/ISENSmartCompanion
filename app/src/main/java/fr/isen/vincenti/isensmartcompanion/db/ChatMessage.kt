package fr.isen.vincenti.isensmartcompanion.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "question") val question: String,
    @ColumnInfo(name = "answer") val answer: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long = System.currentTimeMillis()
)