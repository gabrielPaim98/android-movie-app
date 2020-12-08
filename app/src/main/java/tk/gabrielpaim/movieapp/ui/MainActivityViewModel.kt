package tk.gabrielpaim.movieapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tk.gabrielpaim.movieapp.database.getDatabase
import tk.gabrielpaim.movieapp.repository.MoviesRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val repository = MoviesRepository(database)

    init {
        viewModelScope.launch {
            repository.refreshPopularMovies()
        }
    }

    val popularMovies = repository.popularMovies

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
                return MainActivityViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }
}