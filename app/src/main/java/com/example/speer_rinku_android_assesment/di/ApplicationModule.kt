package com.example.speer_rinku_android_assesment.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.speer_rinku_android_assesment.db.Dao.GitHubUserDao
import com.example.speer_rinku_android_assesment.db.GitHubRoomDatabase
import com.example.speer_rinku_android_assesment.network.GitHubAPI
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun provideApplication(): Application = application


    @Singleton
    @Provides
    fun provideRetrofitGitHubAPi(): GitHubAPI {

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
        }.build()


        val retrofit = Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            baseUrl("https://api.github.com/")
            client(okHttpClient)
        }.build()
        return retrofit.create<GitHubAPI>(GitHubAPI::class.java)
    }


    @Singleton
    @Provides
    fun provideRoomDatabase(app: Application): GitHubRoomDatabase {
        return Room.databaseBuilder(app, GitHubRoomDatabase::class.java, "git_hub_user")
            .build()
    }

    @Singleton
    @Provides
    fun provideGitHubUserDao(db: GitHubRoomDatabase): GitHubUserDao {
        return db.gitHubUserDao()
    }
}