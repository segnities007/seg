package com.segnities007.seg.ui.components.bar.top_search_bar

import androidx.compose.runtime.Immutable

@Immutable
sealed class TopSearchBarAction {
    data class UpdateKeyword(
        val newKeyword: String,
    ) : TopSearchBarAction()
}
