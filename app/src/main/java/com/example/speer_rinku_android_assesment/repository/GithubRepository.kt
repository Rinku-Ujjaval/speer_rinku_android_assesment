package com.example.speer_rinku_android_assesment.repository

import android.util.Log
import com.example.speer_rinku_android_assesment.db.Dao.GitHubUserDao
import com.example.speer_rinku_android_assesment.model.GitHubUser
import com.example.speer_rinku_android_assesment.network.GitHubAPI
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.toString

class GithubRepository @Inject constructor(
    private val gitHubAPI: GitHubAPI,
    private val gitHubUserDao: GitHubUserDao,
) {

    // get from api github user
    fun getGitHubUser(userName: String? = null): Flow<GitHubUser> = flow {
        emit(gitHubAPI.getGitHubUser(userName.toString()))
    }.map {
        coroutineScope {
            addGitHubUser(it)
        }
        it
    }

    // add user in database
    suspend fun addGitHubUser(user: GitHubUser) {
        try {
            gitHubUserDao.insertGitHubUser(user)
        } catch (e: Exception) {
            Log.e("Database", e.message.toString())
        }
    }

    // from database to get Github User List
    fun getDatabaseGitHubUser(userName: String): Flow<List<GitHubUser>> = flow {
        emit(gitHubUserDao.getGitHubUser(userName.toString().toLowerCase()))
    }.map {
        it
    }

    // get follower list
    fun getGitHubUserFollower(userName: String? = null): Flow<List<GitHubUser>> = flow {
        emit(gitHubAPI.getGitHubUserFollowers(userName.toString()))
    }.map {
        it
    }

    // get following list
    fun getGitHubUserFollowing(userName: String? = null): Flow<List<GitHubUser>> = flow {
        emit(gitHubAPI.getGitHubUserFollowing(userName.toString()))
    }.map {
        it
    }


}