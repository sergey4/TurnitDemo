package com.example.turnitdemo.viewmodel

import android.os.Looper
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.turnitdemo.db.Repository
import com.example.turnitdemo.entity.Person
import com.example.turnitdemo.view.PersonListScreenState
import com.example.turnitdemo.view.PersonListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val tag = PersonListViewModel::class.simpleName
    private val _state = mutableStateOf(PersonListScreenState())
    val state: State<PersonListScreenState>
        get() = _state

    private val _listState = mutableStateOf(PersonListState())
    val listState: State<PersonListState>
        get() = _listState

    init {
        observeDb()
    }

    fun onAddClicked() {
        _state.value = _state.value.copy(isLoading = true)
        val personToAdd = getPersonDataFromInput()
        if (personToAdd == null) {
            _state.value = _state.value.copy(isLoading = false)
            // incorrect input, give up :)
            return
        }
        viewModelScope.launch {
            repository.addPerson(personToAdd)
            // also clear input
            _state.value = _state.value.copy(isLoading = false, nameText = "", ageText = "")
        }
    }

    fun onDeleteAllClicked() {
        _state.value = _state.value.copy(showConfirmationDialog = true)
    }

    fun onConfirmDeleteClicked() {
        _state.value = _state.value.copy(showConfirmationDialog = false, isLoading = true)
        viewModelScope.launch {
            try {
                repository.deleteAll()
            } catch (e: Exception) {
                Log.e(tag, "error = $e")
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun onCancelDeleteClicked() {
        _state.value = _state.value.copy(showConfirmationDialog = false)
    }

    fun onNameTextChange(text: String) {
        if (text.length <= 100) {
            _state.value = _state.value.copy(nameText = text)
        }
    }

    fun onAgeTextChange(text: String) {
        if (text.length <= 3) {
            _state.value = _state.value.copy(ageText = text.filter { it.isDigit() })
        }
    }

    private fun observeDb() {
        viewModelScope.launch {
            repository.observeDatabase().collect { persons ->
                _listState.value = PersonListState(persons)
            }
        }
    }

    private fun getPersonDataFromInput(): Person? {
        val currentState = _state.value
        return try {
            val name = currentState.nameText
            val age = currentState.ageText.toInt()
            Person(fullName = name, age = age)
        } catch (e: Exception) {
            Log.e(tag, "error = $e")
            null
        }
    }
}