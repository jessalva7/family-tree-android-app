package com.jessal.familytree.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jessal.familytree.model.Gender
import com.jessal.familytree.model.Person
import com.jessal.familytree.viewmodel.FamilyTreeViewModel

@Composable
fun EditPersonDialog(
    person: Person,
    viewModel: FamilyTreeViewModel,
    onDismiss: () -> Unit,
    onSave: (Person) -> Unit
) {
    var name by remember { mutableStateOf(person.name) }
    var birthYear by remember { mutableStateOf(person.birthYear?.toString() ?: "") }
    var selectedGender by remember { mutableStateOf(person.gender) }
    var selectedParents by remember { mutableStateOf(viewModel.getParents(person.id)) }
    var selectedSpouse by remember { mutableStateOf(viewModel.getSpouse(person.id)) }
    var selectedSiblings by remember { mutableStateOf(viewModel.getSiblings(person.id)) }
    var selectedFriends by remember { mutableStateOf(viewModel.getFriends(person.id)) }
    var showParentDialog by remember { mutableStateOf(false) }
    var showSpouseDialog by remember { mutableStateOf(false) }
    var showSiblingDialog by remember { mutableStateOf(false) }
    var showFriendDialog by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Edit Person", fontWeight = FontWeight.Bold)
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Name field
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Birth Year field
                OutlinedTextField(
                    value = birthYear,
                    onValueChange = { birthYear = it.filter { char -> char.isDigit() } },
                    label = { Text("Birth Year (Optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Gender selection
                Text("Gender", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Gender.entries.forEach { gender ->
                        FilterChip(
                            selected = selectedGender == gender,
                            onClick = { selectedGender = gender },
                            label = { Text(gender.name) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Parents section
                Text("Parents", style = MaterialTheme.typography.titleSmall)
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
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(parent.name, fontSize = 14.sp)
                                TextButton(onClick = {
                                    selectedParents = selectedParents.filter { it.id != parent.id }
                                }) {
                                    Text("Remove", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
                Button(
                    onClick = { showParentDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedParents.size < 2
                ) {
                    Text("Add Parent")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Spouse section
                Text("Spouse", style = MaterialTheme.typography.titleSmall)
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
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(spouse.name, fontSize = 14.sp)
                            TextButton(onClick = { selectedSpouse = null }) {
                                Text("Remove", fontSize = 12.sp)
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

                // Siblings section
                Text("Siblings", style = MaterialTheme.typography.titleSmall)
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
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(sibling.name, fontSize = 14.sp)
                                TextButton(onClick = {
                                    selectedSiblings = selectedSiblings.filter { it.id != sibling.id }
                                }) {
                                    Text("Remove", fontSize = 12.sp)
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

                // Friends section
                Text("Friends", style = MaterialTheme.typography.titleSmall)
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
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(friend.name, fontSize = 14.sp)
                                TextButton(onClick = {
                                    selectedFriends = selectedFriends.filter { it.id != friend.id }
                                }) {
                                    Text("Remove", fontSize = 12.sp)
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
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val updatedPerson = person.copy(
                            name = name,
                            birthYear = birthYear.toIntOrNull(),
                            gender = selectedGender,
                            parentIds = selectedParents.map { it.id },
                            spouseId = selectedSpouse?.id,
                            siblingIds = selectedSiblings.map { it.id },
                            friendIds = selectedFriends.map { it.id }
                        )

                        // Update spouse relationship if changed
                        val oldSpouse = viewModel.getSpouse(person.id)
                        if (oldSpouse?.id != selectedSpouse?.id) {
                            // Remove old spouse relationship
                            oldSpouse?.let {
                                viewModel.updatePerson(it.copy(spouseId = null))
                            }
                            // Add new spouse relationship
                            selectedSpouse?.let {
                                viewModel.updatePerson(it.copy(spouseId = person.id))
                            }
                        }

                        // Update sibling relationships (bidirectional)
                        val oldSiblings = viewModel.getSiblings(person.id)
                        val removedSiblings = oldSiblings.filter { !selectedSiblings.contains(it) }
                        val addedSiblings = selectedSiblings.filter { !oldSiblings.contains(it) }

                        removedSiblings.forEach { sibling ->
                            viewModel.updatePerson(
                                sibling.copy(siblingIds = sibling.siblingIds.filter { it != person.id })
                            )
                        }

                        addedSiblings.forEach { sibling ->
                            viewModel.updatePerson(
                                sibling.copy(siblingIds = sibling.siblingIds + person.id)
                            )
                        }

                        // Update friend relationships (bidirectional)
                        val oldFriends = viewModel.getFriends(person.id)
                        val removedFriends = oldFriends.filter { !selectedFriends.contains(it) }
                        val addedFriends = selectedFriends.filter { !oldFriends.contains(it) }

                        removedFriends.forEach { friend ->
                            viewModel.updatePerson(
                                friend.copy(friendIds = friend.friendIds.filter { it != person.id })
                            )
                        }

                        addedFriends.forEach { friend ->
                            viewModel.updatePerson(
                                friend.copy(friendIds = friend.friendIds + person.id)
                            )
                        }

                        onSave(updatedPerson)
                    }
                },
                enabled = name.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )

    // Parent selection dialog
    if (showParentDialog) {
        AlertDialog(
            onDismissRequest = { showParentDialog = false },
            title = { Text("Select Parent") },
            text = {
                Column {
                    viewModel.people
                        .filter { it.id != person.id && !selectedParents.contains(it) }
                        .forEach { potentialParent ->
                            TextButton(
                                onClick = {
                                    if (selectedParents.size < 2) {
                                        selectedParents = selectedParents + potentialParent
                                    }
                                    showParentDialog = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("${potentialParent.name} (${potentialParent.gender})")
                            }
                        }
                    if (viewModel.people.none { it.id != person.id }) {
                        Text("No other people available")
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
                    viewModel.people
                        .filter { it.id != person.id }
                        .forEach { potentialSpouse ->
                            TextButton(
                                onClick = {
                                    selectedSpouse = potentialSpouse
                                    showSpouseDialog = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("${potentialSpouse.name} (${potentialSpouse.gender})")
                            }
                        }
                    if (viewModel.people.none { it.id != person.id }) {
                        Text("No other people available")
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
                        .filter { it.id != person.id && !selectedSiblings.contains(it) }
                        .forEach { potentialSibling ->
                            TextButton(
                                onClick = {
                                    selectedSiblings = selectedSiblings + potentialSibling
                                    showSiblingDialog = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("${potentialSibling.name} (${potentialSibling.gender})")
                            }
                        }
                    if (viewModel.people.none { it.id != person.id && !selectedSiblings.contains(it) }) {
                        Text("No other people available")
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
                        .filter { it.id != person.id && !selectedFriends.contains(it) }
                        .forEach { potentialFriend ->
                            TextButton(
                                onClick = {
                                    selectedFriends = selectedFriends + potentialFriend
                                    showFriendDialog = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("${potentialFriend.name} (${potentialFriend.gender})")
                            }
                        }
                    if (viewModel.people.none { it.id != person.id && !selectedFriends.contains(it) }) {
                        Text("No other people available")
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

