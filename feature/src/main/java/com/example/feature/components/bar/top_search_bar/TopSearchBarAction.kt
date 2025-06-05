package com.example.feature.components.bar.top_search_bar

import androidx.compose.runtime.Immutable

@Immutable
sealed interface TopSearchBarAction {
    data class UpdateKeyword(
        val newKeyword: String,
    ) : TopSearchBarAction
}
