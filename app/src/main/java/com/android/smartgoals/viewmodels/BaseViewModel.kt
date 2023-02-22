package com.android.smartgoals.viewmodels

import androidx.lifecycle.ViewModel
import com.android.smartgoals.models.ButtonState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel: ViewModel() {
    private val _expandedHint = MutableStateFlow(false)
    val expandedHint: StateFlow<Boolean> = _expandedHint

    private val _expandedExample = MutableStateFlow(false)
    val expandedExample: StateFlow<Boolean> = _expandedExample

    private val _buttonState = MutableStateFlow(ButtonState.INITIAL)
    val buttonState: StateFlow<ButtonState> = _buttonState

    fun onExpandedHint() {
        _expandedHint.value = !_expandedHint.value
    }

    fun onExpandedExample() {
        _expandedExample.value = !_expandedExample.value
    }

    fun onButtonClicked(state: ButtonState) {
        _buttonState.value = state
    }
}