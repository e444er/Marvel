package com.droidli.marvel.domain.repository

import com.droidli.marvel.data.data_source.dto.CharactersDTO

interface MarvelRepository {

    suspend fun getALlCharacter(offset: Int): CharactersDTO

    suspend fun getALlSearchCharacter(search: String): CharactersDTO
}