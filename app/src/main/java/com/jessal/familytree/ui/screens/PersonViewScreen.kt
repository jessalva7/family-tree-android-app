package com.jessal.familytree.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jessal.familytree.model.Gender
import com.jessal.familytree.model.Person
import com.jessal.familytree.ui.components.EditPersonDialog
import com.jessal.familytree.viewmodel.FamilyTreeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonViewScreen(
    viewModel: FamilyTreeViewModel,
    onNavigateBack: () -> Unit,
    initialPersonId: String? = null
) {
    // Start with the specified person or first person (Jessal or whoever is first)
    var currentPersonId by remember {
        mutableStateOf(initialPersonId ?: viewModel.people.firstOrNull()?.id ?: "")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Person View") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (viewModel.people.isEmpty()) {
                Text(
                    text = "No people added yet.\nAdd people to see the family tree.",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                val currentPerson = viewModel.getPersonById(currentPersonId)
                    ?: viewModel.people.firstOrNull()

                currentPerson?.let { person ->
                    FullScreenPersonCard(
                        person = person,
                        viewModel = viewModel,
                        onPersonClick = { personId ->
                            currentPersonId = personId
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FullScreenPersonCard(
    person: Person,
    viewModel: FamilyTreeViewModel,
    onPersonClick: (String) -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }

    val cardColor = when (person.gender) {
        Gender.MALE -> Color(0xFF2196F3)
        Gender.FEMALE -> Color(0xFFE91E63)
        else -> Color(0xFF9C27B0)
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            cardColor,
            cardColor.copy(alpha = 0.8f)
        )
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
        ) {
            // Main Person Card - takes up most of the screen
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Gender Icon
                val genderIcon = when (person.gender) {
                    Gender.MALE -> "♂"
                    Gender.FEMALE -> "♀"
                    else -> "⚧"
                }

                Text(
                    text = genderIcon,
                    fontSize = 80.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Name
                Text(
                    text = person.name,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Birth Year
                person.birthYear?.let { year ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "🎂 Born: $year",
                            fontSize = 24.sp,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Gender Badge
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.15f)
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = person.gender.name,
                        fontSize = 18.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                    )
                }
            }

            // Relationships Section at the bottom
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Relationships",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Parents
                    val parents = viewModel.getParents(person.id)
                    if (parents.isNotEmpty()) {
                        RelationshipSection(
                            title = "👪 Parents",
                            people = parents,
                            onClick = onPersonClick
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Spouse
                    val spouse = viewModel.getSpouse(person.id)
                    spouse?.let {
                        RelationshipSection(
                            title = "💑 Spouse",
                            people = listOf(it),
                            onClick = onPersonClick
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Children
                    val children = viewModel.getChildren(person.id)
                    if (children.isNotEmpty()) {
                        RelationshipSection(
                            title = "👶 Children",
                            people = children,
                            onClick = onPersonClick
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Siblings
                    val siblings = viewModel.getSiblings(person.id)
                    if (siblings.isNotEmpty()) {
                        RelationshipSection(
                            title = "👫 Siblings",
                            people = siblings,
                            onClick = onPersonClick
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Friends
                    val friends = viewModel.getFriends(person.id)
                    if (friends.isNotEmpty()) {
                        RelationshipSection(
                            title = "🤝 Friends",
                            people = friends,
                            onClick = onPersonClick
                        )
                    }

                    // Show message if no relationships
                    if (parents.isEmpty() && spouse == null && children.isEmpty() &&
                        siblings.isEmpty() && friends.isEmpty()) {
                        Text(
                            text = "No relationships yet",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }

        // Floating Edit Button
        FloatingActionButton(
            onClick = { showEditDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Person",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }

    // Edit Dialog
    if (showEditDialog) {
        EditPersonDialog(
            person = person,
            viewModel = viewModel,
            onDismiss = { showEditDialog = false },
            onSave = { updatedPerson ->
                viewModel.updatePerson(updatedPerson)
                showEditDialog = false
            }
        )
    }
}

@Composable
fun RelationshipSection(
    title: String,
    people: List<Person>,
    onClick: (String) -> Unit
) {
    Column {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(people) { person ->
                RelationshipCard(
                    person = person,
                    onClick = { onClick(person.id) }
                )
            }
        }
    }
}

@Composable
fun RelationshipCard(
    person: Person,
    onClick: () -> Unit
) {
    val cardColor = when (person.gender) {
        Gender.MALE -> Color(0xFF2196F3)
        Gender.FEMALE -> Color(0xFFE91E63)
        else -> Color(0xFF9C27B0)
    }

    Card(
        modifier = Modifier
            .width(140.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = cardColor.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Gender Icon
            val genderIcon = when (person.gender) {
                Gender.MALE -> "♂"
                Gender.FEMALE -> "♀"
                else -> "⚧"
            }
            
            Text(
                text = genderIcon,
                fontSize = 32.sp,
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Name
            Text(
                text = person.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1
            )

            // Birth Year
            person.birthYear?.let { year ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Born: $year",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

