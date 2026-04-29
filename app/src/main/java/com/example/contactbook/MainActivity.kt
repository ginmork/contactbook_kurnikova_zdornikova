package com.example.contactbook

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactBookScreen()
        }
    }
}

@Composable
fun ContactBookScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+74951234567"))
            safeStart(context, intent)
        }) { Text("Позвонить") }

        Button(onClick = {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("contact@example.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Обращение")
            }
            safeStart(context, intent)
        }) { Text("Написать email") }

        Button(onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:60.0237,30.2289?q=60.0237,30.2289(Наш офис)"))
            safeStart(context, intent)
        }) { Text("Показать офис на карте") }

        Button(onClick = {
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Контакт: +7 (495) 123-45-67, contact@example.com")
            }
            context.startActivity(Intent.createChooser(sendIntent, "Поделиться через..."))
        }) { Text("Поделиться контактом") }
    }
}

fun safeStart(context: Context, intent: Intent) {
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Нет подходящего приложения", Toast.LENGTH_SHORT).show()
    }
}
