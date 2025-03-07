package fr.isen.vincenti.isensmartcompanion.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(chatMessage: ChatMessage)

    @Query("SELECT * FROM chat_messages ORDER BY timestamp DESC")
    fun getAllMessages(): Flow<List<ChatMessage>>

    @Delete
    suspend fun deleteMessage(chatMessage: ChatMessage)

    @Query("DELETE FROM chat_messages")
    suspend fun deleteAllMessages()
}