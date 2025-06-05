package com.example.feature.screens.hub.comment

fun commentReducer(
    action: CommentAction,
    state: CommentState,
): CommentState =
    when (action) {
        is CommentAction.ProcessOfEngagementAction -> {
            val newComments =
                state.comments.map { post ->
                    if (action.updatedPost.id == post.id) action.updatedPost else post
                }
            state.copy(comments = newComments)
        }

        else -> state
    }
