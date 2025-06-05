package com.example.feature.screens.hub.home

import com.example.domain.model.post.Genre

fun homeReducer(
    action: HomeAction,
    state: HomeState,
): HomeState =
    when (action) {
        is HomeAction.GetBeforeNewPosts,
        is HomeAction.ChangeIsAllPostsFetched,
        -> {
            when ((action as HomeAction.ChangeIsAllPostsFetched).genre) {
                Genre.HAIKU -> state.copy(isAllHaikusFetched = !state.isAllHaikusFetched)
                Genre.TANKA -> state.copy(isAllTankasFetched = !state.isAllTankasFetched)
                Genre.KATAUTA -> state.copy(isAllKatautasFetched = !state.isAllKatautasFetched)
                Genre.SEDOUKA -> state.copy(isAllSedoukasFetched = !state.isAllSedoukasFetched)
                else -> state.copy(isAllPostsFetched = !state.isAllPostsFetched)
            }
        }

        is HomeAction.ChangeEngagementOfPost -> {
            val newPosts =
                when (action.newPost.genre) {
                    Genre.HAIKU ->
                        state.haikus.map { haiku ->
                            if (haiku.id == action.newPost.id) action.newPost else haiku
                        }

                    Genre.TANKA ->
                        state.tankas.map { tanka ->
                            if (tanka.id == action.newPost.id) action.newPost else tanka
                        }

                    Genre.KATAUTA ->
                        state.katautas.map { katauta ->
                            if (katauta.id == action.newPost.id) action.newPost else katauta
                        }

                    Genre.SEDOUKA ->
                        state.sedoukas.map { sedouka ->
                            if (sedouka.id == action.newPost.id) action.newPost else sedouka
                        }

                    else ->
                        state.posts.map { post ->
                            if (post.id == action.newPost.id) action.newPost else post
                        }
                }
            when (action.newPost.genre) {
                Genre.TANKA -> state.copy(tankas = newPosts)
                Genre.HAIKU -> state.copy(haikus = newPosts)
                Genre.KATAUTA -> state.copy(katautas = newPosts)
                Genre.SEDOUKA -> state.copy(sedoukas = newPosts)
                else -> state.copy(posts = newPosts)
            }
        }

        is HomeAction.UpdateCurrentGenre -> state.copy(currentGenre = action.newGenre)

        else -> state
    }
