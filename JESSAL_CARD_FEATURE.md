# Initial "Jessal" Card Feature

## ✅ Feature Implemented

The Family Tree app now starts with an initial **"Jessal" card** in the Tree View!

## What Was Changed

### File: `FamilyTreeViewModel.kt`

Added an `init` block to the ViewModel that creates an initial person named "Jessal" when the app starts:

```kotlin
init {
    // Add initial "Jessal" person to start the family tree
    _people.add(
        Person(
            id = generateId(),
            name = "Jessal",
            birthYear = 1997,
            gender = Gender.MALE,
            parentIds = emptyList(),
            spouseId = null
        )
    )
}
```

## Benefits

1. **Better First Experience**: Users see a sample card immediately instead of an empty screen
2. **Visual Example**: New users can see what the tree view looks like with data
3. **Easy to Start**: Users can add family members related to "Jessal" right away
4. **Demonstration Ready**: The app looks populated from the first launch

## User Experience

### Before:
- App started empty
- Users saw "No people added yet" message
- Had to add first person manually

### After:
- App starts with "Jessal" card visible
- Tree view shows a stylish Material Design card immediately
- Home screen shows 1 person in the list
- Users can:
  - View the existing card in tree view
  - Add parents to Jessal
  - Add spouse to Jessal
  - Add children to Jessal
  - Delete Jessal if they want to start fresh
  - Or keep Jessal and build the tree around it

## Card Details

**Default "Jessal" Person:**
- **Name**: Jessal
- **Birth Year**: 2000
- **Gender**: Male (Blue card in tree view)
- **Parents**: None (root of the tree)
- **Spouse**: None
- **Children**: None

The card appears with all the stylish Material Design features:
- Vibrant blue color
- Rounded corners with shadow
- Gender icon (♂)
- Birth year with 🎂 emoji
- Ready to add relationships

## Next Steps for Users

Users can now:
1. Open the app and see "Jessal" card immediately
2. Tap "View Family Tree" to see the stylish card
3. Add family members related to Jessal
4. Build a complete family tree starting from this root person

The feature is complete and ready to use! 🎉

