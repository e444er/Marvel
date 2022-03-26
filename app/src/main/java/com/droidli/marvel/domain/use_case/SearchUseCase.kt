package com.droidli.marvel.domain.use_case

import com.droidli.marvel.domain.model.Character
import com.droidli.marvel.domain.repository.MarvelRepository
import com.droidli.marvel.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: MarvelRepository
) {

    operator fun invoke(search: String): Flow<Response<List<Character>>> = flow {
        try {
            emit(Response.Loading<List<Character>>())
            val list = repository.getALlSearchCharacter(search = search).data.results.map {
                it.toCharacter()
            }
            emit(Response.Success<List<Character>>(list))
        } catch (e: HttpException) {
            emit(Response.Error<List<Character>>(e.printStackTrace().toString()))
        } catch (e: IOException) {
            emit(Response.Error<List<Character>>(e.printStackTrace().toString()))
        }
    }
}
