package tk.gabrielpaim.movieapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import tk.gabrielpaim.movieapp.domain.PopularMovies
import java.util.concurrent.TimeUnit

const val KEY = "92617104f2646d905240d1f828861df6"

const val PT_BR = "pt-BR"
const val POSTER_PATH = "https://image.tmdb.org/t/p/w500"
const val BASE_URL = "https://api.themoviedb.org/3/"

interface TMDBService {
    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Deferred<PopularMovies>

}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object Network {
    private val requestInterceptor = Interceptor{chain ->

        val url: HttpUrl = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("api_key", KEY)
            .addQueryParameter("language", PT_BR)
            .build()

        val request: Request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val tmdbService = retrofit.create(TMDBService::class.java)
}