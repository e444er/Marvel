package com.droidli.marvel.presentation.CharactersList

import com.droidli.marvel.domain.model.Character

data class MarvelListState(
    val isLoading: Boolean = false,
    val characterList: List<Character> = emptyList(),
    val error: String = ""
)