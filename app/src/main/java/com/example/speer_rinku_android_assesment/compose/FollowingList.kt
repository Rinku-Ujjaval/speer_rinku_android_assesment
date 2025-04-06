package com.example.speer_rinku_android_assesment.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.speer_rinku_android_assesment.base.UIState
import com.example.speer_rinku_android_assesment.viewModel.GitHubViewModel
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowingList(
    gitHubViewModel: GitHubViewModel,
    remNavController: NavHostController,
    userName: String?,
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text("Following List")
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black, titleContentColor = Color.White
            ),
            navigationIcon = {
                IconButton(onClick = { remNavController.navigateUp() }) {
                    androidx.compose.material3.Icon(
                        tint = Color.White,
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )
    }, modifier = Modifier.fillMaxSize()) { innerPadding ->
        val followingList = gitHubViewModel.gitHubUserFollowing.collectAsState().value
        val refreshing = gitHubViewModel.refreshIndicator.collectAsState().value
        Box(modifier = Modifier.padding(innerPadding)) {
            when (followingList) {
                is UIState.Error -> ErrorCompose(followingList.message)
                UIState.Loading -> LoadingCompose()
                is UIState.NoDataFound -> NoDataFoundCompose(followingList.message)
                is UIState.Success -> RefreshLayout(
                    refreshing,
                    // refresh layout
                    gitHubViewModel.getSearchGitHubUserFollower(userName.toString())
                )
                {
                    // get following list
                    FollowerAndFollowingList(followingList.data)
                }
            }
        }
    }
}