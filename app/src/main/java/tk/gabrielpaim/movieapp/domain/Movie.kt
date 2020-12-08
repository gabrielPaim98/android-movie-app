package tk.gabrielpaim.movieapp.domain


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tk.gabrielpaim.movieapp.database.DatabasePopularMovie

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>?,
    val id: Int,
    @Json(name = "original_title")
    val originalTitle: String?,
    val overview: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String?,
    val title: String?,
    @Json(name = "vote_average")
    val voteAverage: Double?
)

fun List<Movie>.asDatabaseModel(): List<DatabasePopularMovie> {
    return map {
        DatabasePopularMovie(
            id = it.id,
            backdropPath = it.backdropPath,
            originalTitle = it.originalTitle,
            overview = it.overview,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            title = it.title,
            voteAverage = it.voteAverage
        )
    }
}