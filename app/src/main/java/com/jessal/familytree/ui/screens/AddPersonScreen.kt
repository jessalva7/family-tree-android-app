package com.jessal.familytree.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jessal.familytree.model.Gender
import com.jessal.familytree.model.Person
import com.jessal.familytree.viewmodel.FamilyTreeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPersonScreen(
    viewModel: FamilyTreeViewModel,
    onNavigateBack: () -> Unit,
    onPersonAdded: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var birthYear by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf(Gender.OTHER) }
    var selectedParents by remember { mutableStateOf(listOf<Person>()) }
    var selectedSpouse by remember { mutableStateOf<Person?>(null) }
    var selectedSiblings by remember { mutableStateOf(listOf<Person>()) }
    var selectedFriends by remember { mutableStateOf(listOf<Person>()) }
    var showParentDialog by remember { mutableStateOf(false) }
    var showSpouseDialog by remember { mutableStateOf(false) }
    var showSiblingDialog by remember { mutableStateOf(false) }
    var showFriendDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Person") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Name field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Birth Year field
            OutlinedTextField(
                value = birthYear,
                onValueChange = { birthYear = it.filter { char -> char.isDigit() } },
                label = { Text("Birth Year (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Gender selection
            Text("Gender", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Gender.values().forEach { gender ->
                    FilterChip(
                        selected = selectedGender == gender,
                        onClick = { selectedGender = gender },
                        label = { Text(gender.name) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Parents selection
            Text("Parents", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            if (selectedParents.isNotEmpty()) {
                selectedParents.forEach { parent ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(parent.name)
                            TextButton(onClick = {
                                selectedParents = selectedParents.filter { it.id != parent.id }
                            }) {
                                Text("Remove")
                            }
                        }
                    }
                }
            }
            Button(
                onClick = { showParentDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Parent")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Spouse selection
            Text("Spouse", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            selectedSpouse?.let { spouse ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(spouse.name)
                        TextButton(onClick = { selectedSpouse = null }) {
                            Text("Remove")
                        }
                    }
                }
            }
            Button(
                onClick = { showSpouseDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (selectedSpouse == null) "Add Spouse" else "Change Spouse")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Siblings selection
            Text("Siblings", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            if (selectedSiblings.isNotEmpty()) {
                selectedSiblings.forEach { sibling ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(sibling.name)
                            TextButton(onClick = {
                                selectedSiblings = selectedSiblings.filter { it.id != sibling.id }
                            }) {
                                Text("Remove")
                            }
                        }
                    }
                }
            }
            Button(
                onClick = { showSiblingDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Sibling")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Friends selection
            Text("Friends", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            if (selectedFriends.isNotEmpty()) {
                selectedFriends.forEach { friend ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(friend.name)
                            TextButton(onClick = {
                                selectedFriends = selectedFriends.filter { it.id != friend.id }
                            }) {
                                Text("Remove")
                            }
                        }
                    }
                }
            }
            Button(
                onClick = { showFriendDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Friend")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Save button
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val person = Person(
                            id = viewModel.generateId(),
                            name = name,
                            birthYear = birthYear.toIntOrNull(),
                            gender = selectedGender,
                            parentIds = selectedParents.map { it.id },
                            spouseId = selectedSpouse?.id,
                            siblingIds = selectedSiblings.map { it.id },
                            friendIds = selectedFriends.map { it.id }
                        )
                        viewModel.addPerson(person)

                        // Update spouse's spouseId if spouse is selected
                        selectedSpouse?.let { spouse ->
                            viewModel.updatePerson(spouse.copy(spouseId = person.id))
                        }

                        // Update siblings bidirectionally
                        selectedSiblings.forEach { sibling ->
                            viewModel.updatePerson(
                                sibling.copy(siblingIds = sibling.siblingIds + person.id)
                            )
                        }

                        // Update friends bidirectionally
                        selectedFriends.forEach { friend ->
                            viewModel.updatePerson(
                                friend.copy(friendIds = friend.friendIds + person.id)
                            )
                        }

                        onPersonAdded()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank()
            ) {
                Text("Save Person")
            }
        }

        // Parent selection dialog
        if (showParentDialog) {
            AlertDialog(
                onDismissRequest = { showParentDialog = false },
                title = { Text("Select Parent") },
                text = {
                    Column {
                        viewModel.people
                            .filter { !selectedParents.contains(it) && selectedParents.size < 2 }
                            .forEach { person ->
                                TextButton(
                                    onClick = {
                                        if (selectedParents.size < 2) {
                                            selectedParents = selectedParents + person
                                        }
                                        showParentDialog = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("${person.name} (${person.gender})")
                                }
                            }
                        if (viewModel.people.isEmpty()) {
                            Text("No people available. Add people first.")
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showParentDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Spouse selection dialog
        if (showSpouseDialog) {
            AlertDialog(
                onDismissRequest = { showSpouseDialog = false },
                title = { Text("Select Spouse") },
                text = {
                    Column {
                        viewModel.people.forEach { person ->
                            TextButton(
                                onClick = {
                                    selectedSpouse = person
                                    showSpouseDialog = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("${person.name} (${person.gender})")
                            }
                        }
                        if (viewModel.people.isEmpty()) {
                            Text("No people available. Add people first.")
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showSpouseDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Sibling selection dialog
        if (showSiblingDialog) {
            AlertDialog(
                onDismissRequest = { showSiblingDialog = false },
                title = { Text("Select Sibling") },
                text = {
                    Column {
                        viewModel.people
                            .filter { !selectedSiblings.contains(it) }
                            .forEach { person ->
                                TextButton(
                                    onClick = {
                                        selectedSiblings = selectedSiblings + person
                                        showSiblingDialog = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("${person.name} (${person.gender})")
                                }
                            }
                        if (viewModel.people.isEmpty()) {
                            Text("No people available. Add people first.")
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showSiblingDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Friend selection dialog
        if (showFriendDialog) {
            AlertDialog(
                onDismissRequest = { showFriendDialog = false },
                title = { Text("Select Friend") },
                text = {
                    Column {
                        viewModel.people
                            .filter { !selectedFriends.contains(it) }
                            .forEach { person ->
                                TextButton(
                                    onClick = {
                                        selectedFriends = selectedFriends + person
                                        showFriendDialog = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("${person.name} (${person.gender})")
                                }
                            }
                        if (viewModel.people.isEmpty()) {
                            Text("No people available. Add people first.")
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showFriendDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

