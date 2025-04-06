package com.example.speer_rinku_android_assesment.network

import com.example.speer_rinku_android_assesment.model.GitHubUser
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubAPI {

    @GET("users/{username}")
    suspend fun getGitHubUser(@Path("username") userName: String): GitHubUser


    @GET("users/{username}/followers")
    suspend fun getGitHubUserFollowers(@Path("username") userName: String): List<GitHubUser>

    @GET("users/{username}/following")
    suspend fun getGitHubUserFollowing(@Path("username") userName: String): List<GitHubUser>


}