# Edit Card Feature - Documentation

## ✨ New Feature: Edit Existing Cards

The Family Tree app now supports **editing existing person cards** directly from the Tree View screen!

## 🎨 UI Components

### **Floating Edit Button**
- **Location**: Bottom-right corner of the screen
- **Design**: Material Design FAB (Floating Action Button)
- **Color**: Primary container color with icon
- **Icon**: Pencil/Edit icon
- **Always visible** when viewing any person card

### **Edit Person Dialog**
- **Full-featured modal dialog** with scrollable content
- **Material Design 3** styling
- **All fields editable**:
  - Name (required)
  - Birth Year (optional)
  - Gender (Male/Female/Other)
  - Parents (up to 2)
  - Spouse (0 or 1)

## 🔧 Features

### **1. Edit Basic Information**
```kotlin
✅ Name - Text field, required
✅ Birth Year - Numeric input, optional
✅ Gender - Three filter chips for selection
```

### **2. Edit Relationships**

#### **Parents**
- View currently selected parents
- Add parent (up to 2 maximum)
- Remove individual parents
- Select from list of all other people
- Auto-disable "Add Parent" when 2 parents selected

#### **Spouse**
- View current spouse if exists
- Change spouse
- Remove spouse
- Select from list of all other people
- **Bidirectional relationship**: Automatically updates spouse's record too

### **3. Smart Relationship Management**

#### **Spouse Relationship Handling**
When you change someone's spouse:
1. **Old spouse** → Their `spouseId` is set to `null`
2. **New spouse** → Their `spouseId` is set to current person's ID
3. **Automatic sync** → Both people's records updated

```kotlin
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
```

## 📱 User Experience

### **How to Edit a Person**

1. **Navigate to Person**
   - Open Tree View
   - Click on relationship cards to navigate to the person you want to edit

2. **Tap Edit Button**
   - Floating button in bottom-right corner
   - Opens edit dialog

3. **Make Changes**
   - Edit any field
   - Add/remove relationships
   - All changes previewed in dialog

4. **Save or Cancel**
   - **Save button**: Applies all changes (disabled if name is blank)
   - **Cancel button**: Discards all changes

5. **See Updates**
   - Card immediately reflects new information
   - Relationships update instantly
   - Navigate to related people to verify changes

## 🎯 Edit Dialog Layout

```
┌────────────────────────────────┐
│  Edit Person            [Title] │
├────────────────────────────────┤
│  ┌──────────────────────────┐  │
│  │ Name: [Text Field]       │  │
│  └──────────────────────────┘  │
│                                │
│  ┌──────────────────────────┐  │
│  │ Birth Year: [Text Field] │  │
│  └──────────────────────────┘  │
│                                │
│  Gender                        │
│  [MALE] [FEMALE] [OTHER]       │
│                                │
│  Parents                       │
│  ┌─────────────────────────┐  │
│  │ John  [Remove]          │  │
│  └─────────────────────────┘  │
│  [Add Parent]                  │
│                                │
│  Spouse                        │
│  ┌─────────────────────────┐  │
│  │ Mary  [Remove]          │  │
│  └─────────────────────────┘  │
│  [Change Spouse]               │
│                                │
├────────────────────────────────┤
│         [Cancel]  [Save]       │
└────────────────────────────────┘
```

## 🔄 State Management

### **Dialog State**
```kotlin
var showEditDialog by remember { mutableStateOf(false) }
var name by remember { mutableStateOf(person.name) }
var birthYear by remember { mutableStateOf(person.birthYear?.toString() ?: "") }
var selectedGender by remember { mutableStateOf(person.gender) }
var selectedParents by remember { mutableStateOf(...) }
var selectedSpouse by remember { mutableStateOf(...) }
```

### **Nested Dialogs**
- Parent selection dialog (modal on modal)
- Spouse selection dialog (modal on modal)
- Clean state management for nested UI

## ✅ Validation

### **Required Fields**
- **Name**: Must not be blank
- Save button disabled when name is empty

### **Optional Fields**
- **Birth Year**: Can be empty
- Validates numeric input only

### **Relationship Constraints**
- Maximum 2 parents
- Maximum 1 spouse
- Cannot select self as parent/spouse

## 🎨 Design Features

### **Material Design 3**
- Floating Action Button for edit trigger
- AlertDialog with scrollable content
- OutlinedTextField for text inputs
- FilterChip for gender selection
- Card components for relationship display
- TextButton for actions

### **Responsive Layout**
- Scrollable dialog content
- Adapts to long lists of people
- Handles varying content heights
- Clean button layout

### **Visual Feedback**
- Disabled states for constraints
- Selected state for gender chips
- Clear relationship cards
- Action buttons with proper colors

## 💡 Benefits

### **User Benefits**
1. ✅ **Fix mistakes** easily
2. ✅ **Update information** as people age
3. ✅ **Modify relationships** when family changes
4. ✅ **No need to delete and recreate** people
5. ✅ **Preserve person ID** and existing relationships

### **Technical Benefits**
1. ✅ **In-place editing** - no navigation away
2. ✅ **Real-time updates** - instant UI refresh
3. ✅ **Relationship integrity** - automatic bidirectional updates
4. ✅ **State preservation** - maintains tree structure
5. ✅ **Reusable dialog** - consistent with add person flow

## 🚀 Integration

### **ViewModel Integration**
```kotlin
viewModel.updatePerson(updatedPerson)
```

Uses existing `updatePerson` method from `FamilyTreeViewModel`:
- Finds person by ID
- Replaces with updated version
- Triggers recomposition
- UI updates automatically

### **Tree View Integration**
- Edit button overlays the full-screen card
- Dialog appears on top of card
- On save, card immediately updates
- Relationships panel reflects changes

## 🎉 Result

Users can now **fully manage their family tree** without leaving the Tree View:
- ✅ Add new people (from home screen)
- ✅ Edit existing people (new feature!)
- ✅ Navigate relationships (click cards)
- ✅ View details (full-screen cards)
- ✅ Delete people (from home screen)

Complete CRUD operations with a beautiful, intuitive interface! 👨‍👩‍👧‍👦✏️

