package com.example.speer_rinku_android_assesment.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.speer_rinku_android_assesment.repository.GithubRepository
import com.example.speer_rinku_android_assesment.viewModel.GitHubViewModel
import javax.inject.Inject


class ViewModelProvider @Inject constructor(val githubRepository: GithubRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GitHubViewModel(githubRepository) as T
    }
}