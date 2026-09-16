package com.example.myapplication

// GIVEN (read it, do not change it)
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.CameraCard
import com.example.myapplication.ui.LevelCard
import com.example.myapplication.ui.LocationCard
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold { inner ->
                    Column(
                        Modifier.padding(inner).padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("LiceoFieldKit",
                            style = MaterialTheme.typography.headlineSmall)
                        LevelCard()
                        CameraCard()
                        LocationCard()
                    }
                }
            }
        }
    }
}
