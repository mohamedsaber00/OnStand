package com.eid.onstand.feature.preview

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PreviewViewModel : ViewModel() {
    private val _text = MutableStateFlow("Hello from PreviewViewModel!")
    val text: StateFlow<String> = _text

    fun updateText(newText: String) {
        _text.value = newText
    }
}