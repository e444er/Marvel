package com.droidli.marvel.data.data_source

import com.droidli.marvel.data.data_source.dto.CharactersDTO
import com.droidli.marvel.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getALlCharacters(
        @Query("apikey") apikey: String = Constants.API_KEY,
        @Query("ts") ts: String = Constants.timeStamp,
        @Query("hash") hash: String = Constants.hash(),
        @Query("offset") offset: String
    ): CharactersDTO

    @GET("/v1/public/characters")
    suspend fun getALlSearchCharacters(
        @Query("apikey") apikey: String = Constants.API_KEY,
        @Query("ts") ts: String = Constants.timeStamp,
        @Query("hash") hash: String = Constants.hash(),
        @Query("nameStartWith") search: String
    ): CharactersDTO
}