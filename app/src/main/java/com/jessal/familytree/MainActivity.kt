package com.jessal.familytree

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jessal.familytree.ui.screens.AddPersonScreen
import com.jessal.familytree.ui.screens.HomeScreen
import com.jessal.familytree.ui.screens.PersonViewScreen
import com.jessal.familytree.ui.theme.FamilyTreeTheme
import com.jessal.familytree.viewmodel.FamilyTreeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FamilyTreeTheme {
                FamilyTreeApp()
            }
        }
    }
}

enum class Screen {
    HOME, ADD_PERSON, PERSON_VIEW
}

@Composable
fun FamilyTreeApp() {
    val viewModel: FamilyTreeViewModel = viewModel()
    var currentScreen by remember { mutableStateOf(Screen.HOME) }
    var selectedPersonId by remember { mutableStateOf<String?>(null) }

    when (currentScreen) {
        Screen.HOME -> {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToAddPerson = { currentScreen = Screen.ADD_PERSON },
                onNavigateToTreeView = {
                    selectedPersonId = null
                    currentScreen = Screen.PERSON_VIEW
                },
                onNavigateToPersonView = { personId ->
                    selectedPersonId = personId
                    currentScreen = Screen.PERSON_VIEW
                }
            )
        }
        Screen.ADD_PERSON -> {
            AddPersonScreen(
                viewModel = viewModel,
                onNavigateBack = { currentScreen = Screen.HOME },
                onPersonAdded = { currentScreen = Screen.HOME }
            )
        }
        Screen.PERSON_VIEW -> {
            PersonViewScreen(
                viewModel = viewModel,
                onNavigateBack = { currentScreen = Screen.HOME },
                initialPersonId = selectedPersonId
            )
        }
    }
}