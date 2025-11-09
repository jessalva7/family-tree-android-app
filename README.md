# Family Tree App

A modern Android application built with Jetpack Compose for creating and visualizing family trees.

## Features

### 1. Add Person
- Add people with detailed information:
  - Name
  - Birth year (optional)
  - Gender (Male, Female, Other)
  - Parents (up to 2)
  - Spouse

### 2. Home Screen
- View list of all people
- See relationships for each person (parents, spouse, children)
- Delete people
- Quick access to add person and tree view

### 3. Tree View
- Visual representation of the family tree
- Color-coded by gender:
  - Blue for Male
  - Pink for Female
  - Purple for Other
- Zoom and pan support using pinch gestures
- Shows hierarchical relationships
- Spouse connections shown in red
- Parent-child connections shown in gray

## Project Structure

```
app/src/main/java/com/jessal/familytree/
├── MainActivity.kt                 # Main entry point with navigation
├── model/
│   └── Person.kt                   # Data model for Person and Gender
├── viewmodel/
│   └── FamilyTreeViewModel.kt      # ViewModel for managing family data
└── ui/
    ├── screens/
    │   ├── HomeScreen.kt           # Home screen with person list
    │   ├── AddPersonScreen.kt      # Form to add new person
    │   └── TreeViewScreen.kt       # Visual family tree view
    └── theme/
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt
```

## Technologies Used

- **Kotlin** - Programming language
- **Jetpack Compose** - Modern UI toolkit
- **Material 3** - Design system
- **ViewModel** - State management
- **Canvas API** - Custom drawing for tree view

## Setup Instructions

1. Open the project in Android Studio
2. Sync Gradle files (File -> Sync Project with Gradle Files)
3. Run the app on an emulator or physical device

## Usage

1. **Add Your First Person**: Tap the + button on the home screen
2. **Build Relationships**: When adding a new person, select their parents or spouse from existing people
3. **View the Tree**: Tap "View Family Tree" to see the visual representation
4. **Navigate**: Use pinch-to-zoom and drag gestures in tree view
5. **Manage**: Delete people from the home screen if needed

## Notes

- The app stores data in memory only (data is lost when app closes)
- You can add up to 2 parents per person
- Spouse relationships are bidirectional (setting A as B's spouse automatically sets B as A's spouse)
- The tree view automatically arranges people by generation

