package com.shilpakala.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

private val CraftOptions = listOf(
    "Wood Carving",
    "Gombe/Toy Making",
    "Lacquerware",
    "Other"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by viewModel.profile.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var isEditing by rememberSaveable { mutableStateOf(false) }
    var name by rememberSaveable { mutableStateOf("") }
    var village by rememberSaveable { mutableStateOf("") }
    var craftType by rememberSaveable { mutableStateOf(CraftOptions.first()) }
    var craftExpanded by remember { mutableStateOf(false) }
    var nameError by rememberSaveable { mutableStateOf(false) }
    var villageError by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(profile, isEditing) {
        if (!isEditing && profile != null) {
            name = profile?.name.orEmpty()
            village = profile?.village.orEmpty()
            craftType = profile?.craftType?.takeIf { it.isNotBlank() } ?: CraftOptions.first()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val showForm = profile == null || isEditing

            if (showForm) {
                Text(text = "Set up your artisan profile")

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        if (it.isNotBlank()) nameError = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Artisan Name") },
                    singleLine = true,
                    isError = nameError,
                    supportingText = {
                        if (nameError) Text("Name is required")
                    }
                )

                OutlinedTextField(
                    value = village,
                    onValueChange = {
                        village = it
                        if (it.isNotBlank()) villageError = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Village") },
                    singleLine = true,
                    isError = villageError,
                    supportingText = {
                        if (villageError) Text("Village is required")
                    }
                )

                ExposedDropdownMenuBox(
                    expanded = craftExpanded,
                    onExpandedChange = { craftExpanded = !craftExpanded }
                ) {
                    OutlinedTextField(
                        value = craftType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Craft Type") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = craftExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = craftExpanded,
                        onDismissRequest = { craftExpanded = false }
                    ) {
                        CraftOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    craftType = option
                                    craftExpanded = false
                                }
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            val trimmedName = name.trim()
                            val trimmedVillage = village.trim()
                            nameError = trimmedName.isEmpty()
                            villageError = trimmedVillage.isEmpty()
                            if (nameError || villageError) return@Button

                            viewModel.saveProfile(
                                name = trimmedName,
                                village = trimmedVillage,
                                craftType = craftType
                            )
                            isEditing = false
                            scope.launch {
                                snackbarHostState.showSnackbar("Profile saved successfully")
                            }
                        }
                    ) {
                        Text("Save Profile")
                    }

                    if (profile != null && isEditing) {
                        Button(
                            onClick = { isEditing = false }
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Name: ${profile?.name.orEmpty()}")
                        Text(text = "Village: ${profile?.village.orEmpty()}")
                        Text(text = "Craft Type: ${profile?.craftType.orEmpty()}")

                        Button(onClick = { isEditing = true }) {
                            Text("Edit")
                        }
                    }
                }
            }
        }
    }
}
