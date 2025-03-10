package fr.isen.vincenti.isensmartcompanion.composable

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import fr.isen.vincenti.isensmartcompanion.R
import fr.isen.vincenti.isensmartcompanion.db.ChatDao
import fr.isen.vincenti.isensmartcompanion.db.ChatMessage
import fr.isen.vincenti.isensmartcompanion.db.DBInstance
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val chatDao: ChatDao = remember { DBInstance.getChatDao(context) }

    var chatHistory by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }

    LaunchedEffect(Unit) {
        chatDao.getAllMessages().collect { messages ->
            chatHistory = messages
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.chat_history),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier
            .height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(chatHistory) { message ->
                HistoryItem(
                    message = message,
                    onDelete = {
                        coroutineScope.launch {
                            chatDao.deleteMessage(message)
                            Toast.makeText(context, context.getString(R.string.message_deleted), Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    chatDao.deleteAllMessages()
                    Toast.makeText(context, context.getString(R.string.history_deleted), Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.red_grenat), contentColor = Color.White),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(stringResource(R.string.delete_history))
        }
    }
}

@Composable
fun HistoryItem(message: ChatMessage, onDelete: () -> Unit) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val formattedDate = dateFormat.format(Date(message.timestamp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Text(text = stringResource(R.string.date) + " $formattedDate", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(3.dp))
        HorizontalDivider( thickness = 1.dp)
        Spacer(modifier = Modifier.height(3.dp))
        Text(text = stringResource(R.string.question) + " ${message.question}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = stringResource(R.string.answer) + " ${message.answer}", style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete), tint = colorResource(id = R.color.red_grenat))
            }
        }
    }
}