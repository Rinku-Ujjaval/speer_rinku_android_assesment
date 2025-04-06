package com.example.speer_rinku_android_assesment.db.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.speer_rinku_android_assesment.model.GitHubUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
@Dao
interface GitHubUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGitHubUser(gitHubUser: GitHubUser)

    @Query("select * from github_user where login = :userName")
    fun getGitHubUser(userName: String): List<GitHubUser>

}