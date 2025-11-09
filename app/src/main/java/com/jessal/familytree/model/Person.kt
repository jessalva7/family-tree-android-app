package com.jessal.familytree.model

data class Person(
    val id: String,
    val name: String,
    val birthYear: Int? = null,
    val gender: Gender = Gender.OTHER,
    val parentIds: List<String> = emptyList(),
    val spouseId: String? = null,
    val siblingIds: List<String> = emptyList(),
    val friendIds: List<String> = emptyList()
)

enum class Gender {
    MALE, FEMALE, OTHER
}

enum class RelationType {
    PARENT, SPOUSE, CHILD, SIBLING, FRIEND
}

