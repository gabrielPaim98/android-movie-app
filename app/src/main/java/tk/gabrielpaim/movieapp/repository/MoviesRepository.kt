package tk.gabrielpaim.movieapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tk.gabrielpaim.movieapp.database.MoviesDatabase
import tk.gabrielpaim.movieapp.database.asDomainModel
import tk.gabrielpaim.movieapp.domain.Movie
import tk.gabrielpaim.movieapp.domain.asDatabaseModel
import tk.gabrielpaim.movieapp.network.Network

class MoviesRepository(private val database: MoviesDatabase) {

    val popularMovies: LiveData<List<Movie>> =
        Transformations.map(database.popularMovieDao.getPopularMovies()) {
            it.asDomainModel()  
        }

    suspend fun refreshPopularMovies() {
        withContext(Dispatchers.IO) {
            val popMovies = Network.tmdbService.getPopularMovies(1).await()
            Log.d("movie", "popMovies.length" + popMovies.movies?.size)
            Log.d("movie", "popMovies" + popMovies.movies?.get(0).toString())
            database.popularMovieDao.insertAll(popMovies.movies!!.asDatabaseModel())
            //popMovies.movies?.asDatabaseModel()?.let { database.popularMovieDao.insertAll(it) }
        }
    }
}