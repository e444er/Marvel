package com.droidli.marvel.presentation.CharactersList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidli.marvel.domain.use_case.CharactersUseCase
import com.droidli.marvel.domain.use_case.SearchUseCase
import com.droidli.marvel.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val charactersUseCase: CharactersUseCase,
    private val searchUseCase: SearchUseCase,
) : ViewModel() {

    private val _marvelValue = MutableStateFlow(MarvelListState())
    var marvelValue: StateFlow<MarvelListState> = _marvelValue

    fun getAllCharactersData(offset: Int) = viewModelScope.launch(Dispatchers.IO) {
        charactersUseCase(offset = offset).collect {
            when (it) {
                is Response.Success -> {
                    _marvelValue.value = MarvelListState(characterList = it.data ?: emptyList())
                }
                is Response.Loading -> {
                    _marvelValue.value = MarvelListState(isLoading = true)
                }
                is Response.Error -> {
                    _marvelValue.value =
                        MarvelListState(error = it.message ?: "An Unexpected Error")
                }
            }
        }
    }
    fun getSearchedCharacters(search:String) = viewModelScope.launch(Dispatchers.IO) {
        searchUseCase.invoke(search = search).collect {
            when (it) {
                is Response.Success -> {
                    _marvelValue.value = MarvelListState(characterList = it.data ?: emptyList())
                }
                is Response.Loading -> {
                    _marvelValue.value = MarvelListState(isLoading = true)
                }
                is Response.Error -> {
                    _marvelValue.value =
                        MarvelListState(error = it.message ?: "An Unexpected Error")
                }
            }
        }
    }

}