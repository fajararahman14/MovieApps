package com.test.movieapps.di

import com.test.movieapps.BuildConfig
import com.test.movieapps.detailmoviescreen.data.DetailMovieApiInterface
import com.test.movieapps.moviescreen.data.MovieApiInterface
import com.test.movieapps.moviescreen.data.MovieRepositoryImpl
import com.test.movieapps.moviescreen.domain.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val timeout = 90L
        return OkHttpClient().newBuilder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", BuildConfig.API_KEY)
                    .build()
                chain.proceed(requestBuilder)
            }
            .callTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    fun provideMovieApiInterface(retrofit: Retrofit): MovieApiInterface {
        return retrofit.create(MovieApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailMovieApiInterface(retrofit: Retrofit): DetailMovieApiInterface {
        return retrofit.create(DetailMovieApiInterface::class.java)
    }


    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApiInterface: MovieApiInterface
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieApiInterface
        )
    }
}