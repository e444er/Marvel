package com.droidli.marvel.data.repository

import com.droidli.marvel.data.data_source.MarvelApi
import com.droidli.marvel.data.data_source.dto.CharactersDTO
import com.droidli.marvel.domain.repository.MarvelRepository
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val api: MarvelApi
) : MarvelRepository {

    override suspend fun getALlCharacter(offset: Int): CharactersDTO {
        return api.getALlCharacters(offset = offset.toString())
    }

    override suspend fun getALlSearchCharacter(search: String): CharactersDTO {
        return api.getALlSearchCharacters(search = search)
    }
}