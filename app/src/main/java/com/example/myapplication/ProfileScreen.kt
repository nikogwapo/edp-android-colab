package com.example.myapplication

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.ProfileTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Profile",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                },
                actions = {
                    IconButton(onClick = onThemeToggle) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Theme"
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // 1. Circular avatar
            Image(
                painter = painterResource(id = R.drawable.profile_photo),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .border(4.dp, MaterialTheme.colorScheme.primaryContainer, CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Full name — bold, largest text
            Text(
                text = "Niko Edryann S. Batasin-in",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // 3. Course + Section subtitle — one line under the name
            Text(
                text = "BSIT • 3-2",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 4. Info Card — groups the fields
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // 5. Five info rows
                    InfoRow(
                        icon = Icons.Default.Person,
                        label = "Full Name",
                        value = "Niko Edryann S. Batasin-in"
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    
                    InfoRow(
                        icon = Icons.Default.School,
                        label = "Course",
                        value = "Bachelor of Science in Information Technology"
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    
                    InfoRow(
                        icon = Icons.Default.Tag,
                        label = "Section",
                        value = "2"
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    
                    InfoRow(
                        icon = Icons.Default.Phone,
                        label = "Mobile No.",
                        value = "09531472227"
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    
                    InfoRow(
                        icon = Icons.Default.Email,
                        label = "Email",
                        value = "batasin-inne92488@liceo.edu.ph"
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun ProfileScreenPreviewLight() {
    ProfileTheme(darkTheme = false) {
        ProfileScreen(isDarkTheme = false, onThemeToggle = {})
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun ProfileScreenPreviewDark() {
    ProfileTheme(darkTheme = true) {
        ProfileScreen(isDarkTheme = true, onThemeToggle = {})
    }
}
