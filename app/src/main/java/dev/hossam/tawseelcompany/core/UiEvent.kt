package com.hossam.tawsel.core

import androidx.navigation.NavDirections

sealed interface UiEvent {
    data class ShowSnackBar(val message: String): UiEvent
    data class Navigate(val destination: Int = 0, val direction: NavDirections? = null): UiEvent
    data class ProgressBar(val isVisible: Boolean = false): UiEvent
    data class Shimmer(val isVisible: Boolean = false): UiEvent
}