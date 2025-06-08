package com.example.feature.screens.hub

fun hubReducer(
    state: HubState,
    action: HubAction,
): HubState =
    when (action) {
        HubAction.ChangeIsHideTopBar,
        -> state.copy(isHideTopBar = !state.isHideTopBar)

        HubAction.ResetIsHideTopBar,
        -> state.copy(isHideTopBar = false)

        is HubAction.SetSelf,
        -> state.copy(self = action.newSelf)

        is HubAction.SetUserID,
        -> state.copy(otherUserID = action.userID)

        is HubAction.AddPostIDToMyLikes -> {
            val newPosts = state.self.likes.plus(action.postID)
            val updatedUser = state.self.copy(likes = newPosts)
            state.copy(self = updatedUser)
        }

        is HubAction.RemovePostIDFromMyLikes -> {
            val newPosts = state.self.likes.minus(action.postID)
            val updatedUser = state.self.copy(likes = newPosts)
            state.copy(self = updatedUser)
        }

        is HubAction.RemovePostIDFromReposts -> {
            val newPosts = state.self.reposts.minus(action.postID)
            val updatedUser = state.self.copy(reposts = newPosts)
            state.copy(self = updatedUser)
        }

        is HubAction.SetAccounts,
        -> state.copy(accounts = action.accounts)

        is HubAction.SetComment,
        -> state.copy(comment = action.comment)

        is HubAction.AddPostIDFromReposts -> {
            val newPosts = state.self.reposts.plus(action.postID)
            val updatedUser = state.self.copy(reposts = newPosts)
            state.copy(self = updatedUser)
        }

        is HubAction.ChangeCurrentRouteName,
        -> state.copy(currentRouteName = action.currentRouteName)

        is HubAction.OpenSnackBar -> {
            state.copy(
                snackBarMessage = action.message,
                isShowSnackBar = true,
            )
        }

        HubAction.CloseSnackBar -> {
            state.copy(
                isShowSnackBar = false,
            )
        }

        else -> state
    }
