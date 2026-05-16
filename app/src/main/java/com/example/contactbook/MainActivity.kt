package com.example.contactbook

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

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
    val emailSubject = stringResource(R.string.email_subject)
    val mapLabel = stringResource(R.string.map_label)
    val shareText = stringResource(R.string.share_text)
    val shareTitle = stringResource(R.string.share_title)

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            val intent = Intent(Intent.ACTION_DIAL, "tel:+74951234567".toUri())
            safeStart(context, intent)
        }) {
            Text(stringResource(R.string.btn_call))
        }

        Button(onClick = {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = "mailto:".toUri()
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("contact@example.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
            safeStart(context, intent)
        }) {
            Text(stringResource(R.string.btn_email))
        }

        Button(onClick = {
            val mapUri = "geo:60.0237,30.2289?q=60.0237,30.2289($mapLabel)"
            val intent = Intent(Intent.ACTION_VIEW, mapUri.toUri())
            safeStart(context, intent)
        }) {
            Text(stringResource(R.string.btn_map))
        }

        Button(onClick = {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, shareText)
            context.startActivity(Intent.createChooser(intent, shareTitle))
        }) {
            Text(stringResource(R.string.btn_share))
        }
    }
}

fun safeStart(context: Context, intent: Intent) {
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, context.getString(R.string.error_no_app), Toast.LENGTH_SHORT).show()
    }
}
