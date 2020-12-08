package tk.gabrielpaim.movieapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import tk.gabrielpaim.movieapp.domain.Movie

@Entity
data class DatabasePopularMovie constructor(
    @PrimaryKey
    val id: Int,
    val backdropPath: String?,
    val originalTitle: String?,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val voteAverage: Double?
)

fun List<DatabasePopularMovie>.asDomainModel(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            backdropPath = it.backdropPath,
            genreIds = null,
            originalTitle = it.originalTitle,
            overview = it.overview,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            title = it.title,
            voteAverage = it.voteAverage
        )
    }
}