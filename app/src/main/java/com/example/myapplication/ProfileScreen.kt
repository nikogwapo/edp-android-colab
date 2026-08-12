package com.example.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProfileForm(
    state: ProfileUiState,
    viewModel: ProfileViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "My Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.name,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Full name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.contactNumber,
            onValueChange = { viewModel.onContactChange(it) },
            label = { Text("Contact number") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.address,
            onValueChange = { viewModel.onAddressChange(it) },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.username,
            onValueChange = { viewModel.onUsernameChange(it) },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Skills",
            fontWeight = FontWeight.Bold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = state.newSkill,
                onValueChange = { viewModel.onNewSkillChange(it) },
                label = { Text("Add a skill") },
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(8.dp))

            Button(
                onClick = { viewModel.addSkill() }
            ) {
                Text("Add")
            }
        }

        state.skills.forEach { skill ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "• $skill",
                    modifier = Modifier.weight(1f)
                )

                TextButton(
                    onClick = { viewModel.removeSkill(skill) }
                ) {
                    Text("Remove")
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { viewModel.showPreview() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Preview")
        }
    }
}

@Composable
fun ProfilePreview(
    state: ProfileUiState,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Profile Preview",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        Text("Name: ${state.name}")
        Text("Email: ${state.email}")
        Text("Contact: ${state.contactNumber}")
        Text("Address: ${state.address}")
        Text("Username: ${state.username}")

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Skills:",
            fontWeight = FontWeight.Bold
        )

        if (state.skills.isEmpty()) {
            Text("No skills added yet.")
        } else {
            state.skills.forEach { skill ->
                Text("• $skill")
            }
        }

        Spacer(Modifier.height(20.dp))

        OutlinedButton(
            onClick = onBack
        ) {
            Text("Back to edit")
        }
    }
}

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    if (state.isPreview) {
        ProfilePreview(
            state = state,
            onBack = { viewModel.backToEdit() }
        )
    } else {
        ProfileForm(
            state = state,
            viewModel = viewModel
        )
    }
}