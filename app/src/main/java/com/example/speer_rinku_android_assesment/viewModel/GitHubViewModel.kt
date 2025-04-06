package com.example.speer_rinku_android_assesment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speer_rinku_android_assesment.repository.GithubRepository
import com.example.speer_rinku_android_assesment.base.UIState
import com.example.speer_rinku_android_assesment.model.GitHubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class GitHubViewModel @Inject constructor(val githubRepository: GithubRepository) : ViewModel() {

    private var _gitHubUser = MutableStateFlow<UIState<GitHubUser>>(UIState.Loading)
    val gitHubUser: StateFlow<UIState<GitHubUser>> = _gitHubUser

    private var _gitHubUserFollower = MutableStateFlow<UIState<List<GitHubUser>>>(UIState.Loading)
    val gitHubUserFollower: StateFlow<UIState<List<GitHubUser>>> = _gitHubUserFollower

    private var _gitHubUserFollowing = MutableStateFlow<UIState<List<GitHubUser>>>(UIState.Loading)
    val gitHubUserFollowing: StateFlow<UIState<List<GitHubUser>>> = _gitHubUserFollowing

    private var _isRefreshIndicator = MutableStateFlow<Boolean>(false)
    val refreshIndicator: StateFlow<Boolean> = _isRefreshIndicator


    fun getSearchGitHubUser(userName: String) {
        viewModelScope.launch {
            _isRefreshIndicator.emit(true)
            githubRepository.getGitHubUser(userName).catch { it ->
                _gitHubUser.value = UIState.Error(it.message.toString())
            }.flowOn(Dispatchers.IO).collect { it ->
                if (it.login == null) {
                    _gitHubUser.value = UIState.NoDataFound("User Not Found")
                } else {
                    _gitHubUser.value = UIState.Success(it)
                }
            }
            _isRefreshIndicator.emit(false)
        }
    }

    fun getSearchGitHubUserDb(userName: String) {
        viewModelScope.launch {
            _isRefreshIndicator.emit(true)
            githubRepository.getDatabaseGitHubUser(userName).catch { it ->
                _gitHubUser.value = UIState.Error(it.message.toString())
            }.flowOn(Dispatchers.IO).collect { it ->
                if (it.isEmpty()) {
                    _gitHubUser.value = UIState.NoDataFound("User Not Found")
                } else {
                    _gitHubUser.value = UIState.Success(it.first())
                }
            }
            _isRefreshIndicator.emit(false)
        }
    }

    fun getSearchGitHubUserFollower(userName: String) {
        viewModelScope.launch {
            _isRefreshIndicator.value = true
            githubRepository.getGitHubUserFollower(userName).catch { it ->
                _gitHubUser.value = UIState.Error(it.message.toString())
            }.flowOn(Dispatchers.IO).collect { it ->
                if (it.isEmpty()) {
                    _gitHubUserFollower.value = UIState.NoDataFound("Follower Not Found")
                } else {
                    _gitHubUserFollower.value = UIState.Success(it)
                }
            }
            _isRefreshIndicator.value = false

        }
    }

    fun getSearchGitHubUserFollowing(userName: String) {
        viewModelScope.launch {
            _isRefreshIndicator.value = true
            githubRepository.getGitHubUserFollowing(userName).catch { it ->
                UIState.Error(it.message.toString())
            }.flowOn(Dispatchers.IO).collect { it ->
                if (it.isEmpty()) {
                    _gitHubUserFollowing.value = UIState.NoDataFound("Following Not Found")
                } else {
                    _gitHubUserFollowing.value = UIState.Success(it)
                }
            }
            _isRefreshIndicator.value = false
        }
    }

}