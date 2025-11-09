package com.jessal.familytree.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.jessal.familytree.model.Gender
import com.jessal.familytree.model.Person
import java.util.UUID

class FamilyTreeViewModel : ViewModel() {
    private val _people = mutableStateListOf<Person>()
    val people: List<Person> get() = _people

    init {
        // Add initial "Jessal" person to start the family tree
        _people.add(
            Person(
                id = generateId(),
                name = "Jessal",
                birthYear = 2025,
                gender = Gender.MALE,
                parentIds = emptyList(),
                spouseId = null
            )
        )
    }

    fun addPerson(person: Person) {
        _people.add(person)
    }

    fun updatePerson(person: Person) {
        val index = _people.indexOfFirst { it.id == person.id }
        if (index != -1) {
            _people[index] = person
        }
    }

    fun deletePerson(personId: String) {
        _people.removeAll { it.id == personId }
    }

    fun getPersonById(id: String): Person? {
        return _people.find { it.id == id }
    }

    fun generateId(): String {
        return UUID.randomUUID().toString()
    }

    fun getChildren(parentId: String): List<Person> {
        return _people.filter { it.parentIds.contains(parentId) }
    }

    fun getParents(personId: String): List<Person> {
        val person = getPersonById(personId) ?: return emptyList()
        return person.parentIds.mapNotNull { getPersonById(it) }
    }

    fun getSpouse(personId: String): Person? {
        val person = getPersonById(personId) ?: return null
        return person.spouseId?.let { getPersonById(it) }
    }

    fun getSiblings(personId: String): List<Person> {
        val person = getPersonById(personId) ?: return emptyList()
        return person.siblingIds.mapNotNull { getPersonById(it) }
    }

    fun getFriends(personId: String): List<Person> {
        val person = getPersonById(personId) ?: return emptyList()
        return person.friendIds.mapNotNull { getPersonById(it) }
    }
}
