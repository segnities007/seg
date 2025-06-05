package com.example.feature.screens.hub.post

fun postReducer(
    state: PostState,
    action: PostAction,
): PostState =
    when (action) {
        is PostAction.UpdateIsLoading -> state.copy(isLoading = action.isLoading)
        is PostAction.UpdateGenre -> state.copy(genre = action.newGenre)
        is PostAction.UpdateInputText -> state.copy(inputText = action.newInputText)
        else -> state
    }
