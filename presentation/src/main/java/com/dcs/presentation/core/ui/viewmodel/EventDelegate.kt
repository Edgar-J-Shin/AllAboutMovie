package com.dcs.presentation.core.ui.viewmodel

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface EventDelegate<O, I> {
    val effect: SharedFlow<O>

    suspend fun emitEffect(effect: O)

    fun dispatchEvent(event: I)

    class EventDelegateImpl<O, I> : EventDelegate<O, I> {
        private val _effect = MutableSharedFlow<O>()
        override val effect = _effect.asSharedFlow()

        override suspend fun emitEffect(effect: O) {
            _effect.emit(effect)
        }

        override fun dispatchEvent(event: I) {}
    }
}
