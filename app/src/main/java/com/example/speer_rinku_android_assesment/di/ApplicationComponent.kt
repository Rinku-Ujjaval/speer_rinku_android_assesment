package com.example.speer_rinku_android_assesment.di

import com.example.speer_rinku_android_assesment.MainActivity
import com.example.speer_rinku_android_assesment.db.Dao.GitHubUserDao
import com.example.speer_rinku_android_assesment.network.GitHubAPI
import com.example.speer_rinku_android_assesment.repository.GithubRepository
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

}