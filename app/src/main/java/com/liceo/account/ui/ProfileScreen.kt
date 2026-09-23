package com.liceo.account.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.liceo.account.domain.model.User
import com.liceo.account.ui.theme.LiceoAccountTheme
import java.util.Calendar

@Composable
fun ProfileScreen(user: User, onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // TODO 12a: a green Card that says "You successfully logged in!"
        // and under it "Welcome back, <fullName>."
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    "You successfully logged in!",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )
                Text("Welcome back, ${user.fullName}.")
            }
        }

        // TODO 12b: Text "My Profile" as a heading
        Text("My Profile", style = MaterialTheme.typography.headlineSmall)

        // TODO 12c: four ProfileRow(...) calls: Full name, Email, Birthdate, User ID
        ProfileRow("Full name", user.fullName)
        ProfileRow("Email", user.email)
        ProfileRow("Birthdate", user.birthdate)
        ProfileRow("User ID", user.id)

        // BONUS TODO 14d (Part G only): an "Age" row goes here
        ageFrom(user.birthdate)?.let {
            ProfileRow("Age", "$it years old")
        }

        // TODO 12d: Button "Log out" -> onLogout()
        Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
            Text("Log out")
        }
    }
}

// GIVEN (read it, do not change it): one label with its value under it
@Composable
fun ProfileRow(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}

// Returns the age in years, or null if the birthdate cannot be read.
fun ageFrom(birthdate: String): Int? {
    // TODO 14a: split "2004-05-17" at "-" into year, month, day as Int?
    // (use toIntOrNull); if the split does not give 3 parts, return null
    val parts = birthdate.split("-")
    if (parts.size != 3) return null
    val y = parts[0].toIntOrNull() ?: return null
    val m = parts[1].toIntOrNull() ?: return null
    val d = parts[2].toIntOrNull() ?: return null

    // TODO 14b: read today's year, month and day from Calendar.getInstance()
    val now = Calendar.getInstance()
    val ty = now.get(Calendar.YEAR)
    val tm = now.get(Calendar.MONTH) + 1
    val td = now.get(Calendar.DAY_OF_MONTH)

    // TODO 14c: age = this year - birth year, minus 1 if the birthday
    // has not happened yet this year; return age
    var age = ty - y
    if (tm < m || (tm == m && td < d)) {
        age -= 1
    }
    return age
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    LiceoAccountTheme {
        ProfileScreen(
            user = User(
                id = "12",
                fullName = "Juan Dela Cruz",
                email = "juan.test01@liceo.test",
                birthdate = "2004-05-17"
            ),
            onLogout = {}
        )
    }
}

