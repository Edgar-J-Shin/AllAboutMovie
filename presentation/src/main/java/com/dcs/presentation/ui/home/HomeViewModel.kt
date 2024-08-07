package com.dcs.presentation.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val _queryText = MutableStateFlow("")
    val queryText = _queryText.asStateFlow()

    fun onSearchTextChange(text: String) {
        _queryText.value = text
    }

    fun onSearchTextClear() {
        _queryText.value = ""
    }
}
