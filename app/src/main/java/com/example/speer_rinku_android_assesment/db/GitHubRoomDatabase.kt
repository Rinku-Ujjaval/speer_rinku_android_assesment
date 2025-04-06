package com.example.speer_rinku_android_assesment.db

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.speer_rinku_android_assesment.db.Dao.GitHubUserDao
import com.example.speer_rinku_android_assesment.model.GitHubUser

@Database(entities = [GitHubUser::class], version = 1)
abstract class GitHubRoomDatabase : RoomDatabase() {
    abstract fun gitHubUserDao(): GitHubUserDao
}