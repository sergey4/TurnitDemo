package com.example.turnitdemo.view

import com.example.turnitdemo.entity.Person

data class PersonListScreenState(
    val nameText: String = "",
    val ageText: String = "",
    val isLoading: Boolean = false,
    val showConfirmationDialog: Boolean = false
) {
    val isAddButtonEnabled = nameText.isNotBlank() && ageText.isNotBlank()
}

data class PersonListState(
    val persons: List<Person> = emptyList()
)