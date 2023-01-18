package com.android.smartgoals.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

//the activity view model
class MainViewModel: ViewModel() {
    private val _isSpecificCompleted = MutableStateFlow(false)
    val isSpecificCompleted : StateFlow<Boolean> = _isSpecificCompleted

    private val _specificDescription = MutableStateFlow("")

    val specificGoalComponent: StateFlow<GoalComponent> = combine(
        _isSpecificCompleted,
        _specificDescription
    ) { completed, description ->
        return@combine getSpecificGoalComponent(completed, description)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, GoalComponent())

    private fun getSpecificGoalComponent(completed: Boolean, description: String): GoalComponent {
        return GoalComponent(completed, description)
    }

    fun setSpecificState(isCompleted: Boolean) {
        _isSpecificCompleted.value = isCompleted
    }

    //TODO this is a placeholder for local storage
    fun setSpecificGoalDescription(text: String) {
        _specificDescription.value = text
    }
}

data class GoalComponent(val completed: Boolean = false, val description: String = "")