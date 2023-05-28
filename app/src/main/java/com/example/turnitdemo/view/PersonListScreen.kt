package com.example.turnitdemo.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.turnitdemo.R
import com.example.turnitdemo.entity.Person
import com.example.turnitdemo.viewmodel.PersonListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonListScreen(
    viewModel: PersonListViewModel = viewModel()
) {
    val state = viewModel.state
    val listState = viewModel.listState
    val landscape = isLandscapeOrientation()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!landscape)
                TopAppBar(title = {
                    Text(stringResource(id = R.string.app_name))
                })
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            NameInputPanel(
                nameText = state.value.nameText,
                onNameTextChange = { text -> viewModel.onNameTextChange(text) },
                isLoading = state.value.isLoading,
                isAddButtonEnabled = state.value.isAddButtonEnabled,
                isDeleteButtonEnabled = listState.value.persons.isNotEmpty(),
                onAddClick = { viewModel.onAddClicked() },
                onDeleteAllClick = { viewModel.onDeleteAllClicked() }
            )
            PersonListPanel(listState.value.persons)
        }
    }
    if (state.value.showConfirmationDialog) {
        ConfirmationDialog(
            title = stringResource(id = R.string.title_delete_all),
            text = stringResource(id = R.string.text_confirm_delete_all),
            onDismissClick = { viewModel.onCancelDeleteClicked() },
            onConfirmClick = { viewModel.onConfirmDeleteClicked() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NameInputPanel(
    nameText: String,
    onNameTextChange: (String) -> Unit,
    isLoading: Boolean,
    isAddButtonEnabled: Boolean,
    isDeleteButtonEnabled: Boolean,
    onAddClick: () -> Unit,
    onDeleteAllClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .widthIn(max = dimensionResource(id = R.dimen.max_row_width))
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically){
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = !isLoading,
                value = nameText,
                onValueChange = { onNameTextChange(it) },
                label = { Text(stringResource(id = R.string.label_name)) },
                maxLines = 1,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
            ){
                Button(
                    onClick = onAddClick,
                    enabled = isAddButtonEnabled && !isLoading
                ) {
                    Text(stringResource(id = R.string.button_add))
                }
                Button(
                    onClick = onDeleteAllClick,
                    enabled = isDeleteButtonEnabled && !isLoading
                ) {
                    Text(stringResource(id = R.string.button_delete_all))
                }
            }
        }
    }
}

@Composable
private fun PersonListPanel(persons: List<Person>) {
    if (persons.isEmpty()) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.text_list_is_empty),
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(persons) {person ->
                PersonListItem(person)
            }
        }
    }
}

@Composable
private fun PersonListItem(person: Person) {
    Card {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                modifier = Modifier.weight(1f),
                text = person.fullName,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(person.age.toString())
        }
    }
}
