package tk.gabrielpaim.movieapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PopularMovieDao {
    @Query("SELECT * FROM databasePopularMovie")
    fun getPopularMovies(): LiveData<List<DatabasePopularMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<DatabasePopularMovie>)
}

@Database(entities = [DatabasePopularMovie::class], version = 1)
abstract class MoviesDatabase: RoomDatabase(){
    abstract val popularMovieDao: PopularMovieDao
}

private lateinit var INSTANCE: MoviesDatabase

fun getDatabase(context: Context): MoviesDatabase {
    synchronized(MoviesDatabase::class.java) {
        if (!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                MoviesDatabase::class.java,
                "popularMovies").build()
        }
    }

    return INSTANCE
}