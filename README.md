# Family Tree - Android App

A modern, feature-rich Android application for creating and managing family trees with an intuitive Material Design 3 interface. Built with Jetpack Compose and Kotlin.

## 📱 Features

### Core Functionality
- **Person Management** - Add, edit, and delete family members with detailed information
- **Rich Relationships** - Support for 5 relationship types:
  - 👪 Parents (up to 2)
  - 💑 Spouse (bidirectional)
  - 👶 Children (unlimited)
  - 👫 Siblings (bidirectional)
  - 🤝 Friends (bidirectional)

### User Interface
- **PersonView** - Full-screen card display with gradient backgrounds and navigation
- **Search Bar** - Real-time filtering by name, gender, or birth year with highlighted results
- **Interactive Cards** - Click any person card to view their full details
- **Relationship Navigation** - Tap relationship cards to explore the family tree
- **Material Design 3** - Modern UI with beautiful animations and theming

### Smart Features
- **Bidirectional Relationships** - Automatically sync spouse, sibling, and friend connections
- **Visual Highlighting** - Search results highlighted with color-coded cards
- **Gender Icons** - Visual indicators (♂ ♀ ⚧) with color-coding
- **Edit Anywhere** - Edit button on every person card for quick updates
- **Empty States** - Helpful messages guide users through the app

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Design System**: Material Design 3
- **State Management**: Compose State & ViewModel
- **Min SDK**: 26 (Android 8.0 Oreo)
- **Target SDK**: 34 (Android 14)

## 📂 Project Structure

```
app/src/main/java/com/jessal/familytree/
├── MainActivity.kt                      # Main entry point & navigation
├── model/
│   └── Person.kt                        # Data models (Person, Gender, RelationType)
├── viewmodel/
│   └── FamilyTreeViewModel.kt           # Business logic & state management
├── ui/
│   ├── components/
│   │   └── EditPersonDialog.kt          # Reusable edit dialog component
│   ├── screens/
│   │   ├── HomeScreen.kt                # Main list with search
│   │   ├── AddPersonScreen.kt           # Add new person form
│   │   └── PersonViewScreen.kt          # Full-screen person display
│   └── theme/
│       ├── Color.kt                     # App colors
│       ├── Theme.kt                     # Material theme configuration
│       └── Type.kt                      # Typography
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 11 or higher
- Android SDK 26+

### Installation
1. Clone the repository
   ```bash
   git clone https://github.com/yourusername/FamilyTree.git
   ```

2. Open the project in Android Studio
   ```bash
   cd FamilyTree
   # Open in Android Studio or use:
   studio .
   ```

3. Sync Gradle files
   - File → Sync Project with Gradle Files

4. Run the app
   - Select an emulator or connected device
   - Click Run ▶️ (or press Shift+F10)

## 📖 Usage

### Adding People
1. Tap the **+** floating action button on the home screen
2. Fill in the person's details (name, birth year, gender)
3. Select relationships (parents, spouse, siblings, friends)
4. Tap **Save Person**

### Searching
1. Use the search bar on the home screen
2. Type any part of a name, gender, or birth year
3. Results filter in real-time with highlighted matches

### Viewing Details
1. **Option 1**: Click any person card → Opens PersonView with that person
2. **Option 2**: Tap "View Person" button → Opens with first person (Jessal)
3. Navigate through relationships by tapping cards at the bottom

### Editing People
1. **From Home Screen**: Tap the ✏️ edit icon on any card
2. **From PersonView**: Tap the floating edit button (bottom-right)
3. Modify any information or relationships
4. Tap **Save** to apply changes

## 🎨 Features Showcase

### Person View
- **Full-screen gradient background** based on gender
- **Large profile display** with gender icon and name
- **Relationship panel** at bottom showing all connections
- **Click to navigate** - Tap any relationship to view that person

### Search & Filter
- **Real-time search** as you type
- **Multi-field matching** - name, gender, or birth year
- **Visual highlighting** - Matching cards stand out
- **Result count** - Always know how many matches

### Relationship Management
- **Bidirectional sync** - Changes update both people automatically
- **Easy addition** - Select from existing people
- **Quick removal** - One tap to remove connections
- **Validation** - Prevents invalid relationships (e.g., max 2 parents)

## 🔄 Navigation Flow

```
Home Screen
├─ Search & filter people
├─ Click card → PersonView (that person)
├─ "View Person" → PersonView (first person)
├─ [+] Add → AddPersonScreen
└─ Edit/Delete buttons

PersonView
├─ See all relationships
├─ Click relationship → PersonView (new person)
├─ Edit FAB → Edit dialog
└─ Back → Home Screen
```

## 🎯 Roadmap

### Planned Features
- [ ] Data persistence (Room database or Firebase)
- [ ] Export family tree (PDF, image)
- [ ] Photo support for people
- [ ] Family tree visualization (graph view)
- [ ] Import/export data (JSON, GEDCOM)
- [ ] Dark mode support
- [ ] Share family trees
- [ ] Statistics & insights

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Jessal**

## 🙏 Acknowledgments

- Built with [Jetpack Compose](https://developer.android.com/jetpack/compose)
- Icons from [Material Design Icons](https://fonts.google.com/icons)
- Inspired by the need for simple, beautiful family tree management

## 📸 Screenshots

<!-- Add screenshots here -->
_Screenshots coming soon_

## 💡 Support

For support, please open an issue in the GitHub repository.

---

**Made with ❤️ using Jetpack Compose**

