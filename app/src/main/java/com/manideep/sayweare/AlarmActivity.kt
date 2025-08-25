package com.manideep.sayweare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manideep.sayweare.ui.theme.SayWeAreTheme

class AlarmActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			SayWeAreTheme {
				AlarmUIContent()
			}
		}
	}
}

@Composable
private fun AlarmUIContent() {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(24.dp),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text("SayWeAre", fontSize = 32.sp, fontWeight = FontWeight.Bold)
		Spacer(Modifier.height(16.dp))
		Text("It's an Emergency", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
		Spacer(Modifier.height(24.dp))
		Button(
			onClick = { /* TODO: mark claim */ },
			colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
		) { Text("I'm the Savior") }
		Spacer(Modifier.height(12.dp))
		Button(
			onClick = { /* TODO: decline */ },
			colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828))
		) { Text("I'm Sorry") }
	}
}