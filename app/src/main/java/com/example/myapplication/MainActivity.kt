package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReactiveScreen()
                }
            }
        }
    }
}

@Composable
fun ReactiveScreen() {
    // 'count' state for Part A/D
    var count by remember { mutableStateOf(0) }
    
    // 'name' state for Part B/C (using rememberSaveable to survive rotation)
    var name by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Part B: Greeting
        Text(
            text = if (name.isBlank()) "Hello, stranger!" else "Hello, $name!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(Modifier.height(16.dp))
        
        // Part B: Name Input
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") }
        )
        
        Spacer(Modifier.height(32.dp))

        // Part D: Hoisted Counter Controls
        CounterControls(
            count = count,
            onIncrement = { count++ },
            onDecrement = { count-- },
            onReset = { count = 0 }
        )
    }
}

@Composable
fun CounterControls(
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Count: $count", fontSize = 24.sp)
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onDecrement) { Text("–") }
            Button(onClick = onReset) { Text("Reset") }
            Button(onClick = onIncrement) { Text("+") }
        }
    }
}
