package com.dcs.presentation.core.ui.viewmodel

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface EventDelegate<EF, EV> {
    val effect: SharedFlow<EF>

    suspend fun emitEffect(effect: EF)

    fun dispatchEvent(event: EV)

    class EventDelegateImpl<EF, EV> : EventDelegate<EF, EV> {
        private val _effect = MutableSharedFlow<EF>()
        override val effect = _effect.asSharedFlow()

        override suspend fun emitEffect(effect: EF) {
            _effect.emit(effect)
        }

        override fun dispatchEvent(event: EV) {}
    }
}
