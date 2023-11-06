package com.example.kmpmovie.data.repository

import com.example.kmpmovie.data.remote.ApiImpl
import com.example.kmpmovie.utils.network.DataState
import com.seiko.imageloader.model.DataSource
import kotlinx.coroutines.flow.flow

class MovieRepository {
    private val api = ApiImpl()

    fun nowPlayingMovie(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = api.nowPlayingMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun popularMovie(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = api.popularMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun topRatedMovie(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = api.topRatedMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun upComingMovie(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = api.upcomingMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun movieDetail(movieId: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = api.movieDetail(movieId)
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun searchMovie(searchKey: String) = flow {
        emit(DataState.Loading)
        try {
            val result = api.movieSearch(searchKey)
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}