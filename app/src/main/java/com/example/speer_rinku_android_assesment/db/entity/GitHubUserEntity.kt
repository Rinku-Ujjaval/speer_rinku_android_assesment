package com.example.speer_rinku_android_assesment.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "github_user")
data class GitHubUserEntity(
    @PrimaryKey
    val login: String? = null,
    val id: String? = null,
    val avatar_url: String? = null,
    val name: String? = null,
    val followers: Int? = null,
    val following: Int? = null,
    val bio: String? = null
) {

}