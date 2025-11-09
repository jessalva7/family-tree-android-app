package com.jessal.familytree.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jessal.familytree.model.Person
import com.jessal.familytree.ui.components.EditPersonDialog
import com.jessal.familytree.viewmodel.FamilyTreeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: FamilyTreeViewModel,
    onNavigateToAddPerson: () -> Unit,
    onNavigateToTreeView: () -> Unit,
    onNavigateToPersonView: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    // Filter people based on search query
    val filteredPeople = remember(searchQuery, viewModel.people) {
        if (searchQuery.isBlank()) {
            viewModel.people
        } else {
            viewModel.people.filter { person ->
                person.name.contains(searchQuery, ignoreCase = true) ||
                person.gender.name.contains(searchQuery, ignoreCase = true) ||
                person.birthYear?.toString()?.contains(searchQuery) == true
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Family Tree") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddPerson) {
                Icon(Icons.Default.Add, contentDescription = "Add Person")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // View Person Button
            Button(
                onClick = onNavigateToTreeView,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Person")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search by name, gender, or birth year...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = {
                            searchQuery = ""
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // People List Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (searchQuery.isBlank()) {
                        "People (${viewModel.people.size})"
                    } else {
                        "Results (${filteredPeople.size})"
                    },
                    style = MaterialTheme.typography.titleLarge
                )

                if (searchQuery.isNotBlank() && filteredPeople.size != viewModel.people.size) {
                    TextButton(onClick = {
                        searchQuery = ""
                    }) {
                        Text("Clear filter")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (viewModel.people.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No people added yet.\nTap + to add a person.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else if (filteredPeople.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No matches found for \"$searchQuery\"",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredPeople) { person ->
                        PersonCard(
                            person = person,
                            viewModel = viewModel,
                            searchQuery = searchQuery,
                            onCardClick = { onNavigateToPersonView(person.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PersonCard(
    person: Person,
    viewModel: FamilyTreeViewModel,
    searchQuery: String = "",
    onCardClick: () -> Unit = {}
) {
    var showEditDialog by remember { mutableStateOf(false) }

    // Highlight search matches
    val isMatch = searchQuery.isNotBlank() && (
        person.name.contains(searchQuery, ignoreCase = true) ||
        person.gender.name.contains(searchQuery, ignoreCase = true) ||
        person.birthYear?.toString()?.contains(searchQuery) == true
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCardClick),
        colors = if (isMatch) {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            )
        } else {
            CardDefaults.cardColors()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = person.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = if (person.name.contains(searchQuery, ignoreCase = true))
                        FontWeight.Bold else FontWeight.Normal
                )
                Text(
                    text = buildString {
                        append(person.gender.name)
                        person.birthYear?.let { append(" • Born: $it") }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Show relationships
                val parents = viewModel.getParents(person.id)
                if (parents.isNotEmpty()) {
                    Text(
                        text = "Parents: ${parents.joinToString(", ") { it.name }}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                val spouse = viewModel.getSpouse(person.id)
                spouse?.let {
                    Text(
                        text = "Spouse: ${it.name}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                val children = viewModel.getChildren(person.id)
                if (children.isNotEmpty()) {
                    Text(
                        text = "Children: ${children.joinToString(", ") { it.name }}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                val siblings = viewModel.getSiblings(person.id)
                if (siblings.isNotEmpty()) {
                    Text(
                        text = "Siblings: ${siblings.joinToString(", ") { it.name }}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                val friends = viewModel.getFriends(person.id)
                if (friends.isNotEmpty()) {
                    Text(
                        text = "Friends: ${friends.joinToString(", ") { it.name }}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row {
                IconButton(onClick = { showEditDialog = true }) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(onClick = { viewModel.deletePerson(person.id) }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }

    // Edit Dialog
    if (showEditDialog) {
        EditPersonDialog(
            person = person,
            viewModel = viewModel,
            onDismiss = { showEditDialog = false },
            onSave = { updatedPerson: Person ->
                viewModel.updatePerson(updatedPerson)
                showEditDialog = false
            }
        )
    }
}

