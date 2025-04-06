package com.example.speer_rinku_android_assesment.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.speer_rinku_android_assesment.base.NavRoute
import com.example.speer_rinku_android_assesment.base.UIState
import com.example.speer_rinku_android_assesment.isOnline
import com.example.speer_rinku_android_assesment.viewModel.GitHubViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GitHubUserSearch(gitHubViewModel: GitHubViewModel, remNavController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text("GitHub Users Search")
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black, titleContentColor = Color.White
            ),
        )
    }, modifier = Modifier.fillMaxSize()) { innerPadding ->
        Greeting(
            modifier = Modifier.padding(innerPadding),
            gitHubViewModel,
            remNavController
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun Greeting(
    modifier: Modifier,
    gitHubViewModel: GitHubViewModel,
    remNavController: NavHostController,
) {
    // add pull refresh indicator
    val refreshing = gitHubViewModel.refreshIndicator.collectAsState()
    var searchUSer = remember { mutableStateOf("") }


    val gitHubUser = gitHubViewModel.gitHubUser.collectAsState().value

    if (searchUSer.value.isNotEmpty()) {
        if (isOnline(LocalContext.current)) {
            gitHubViewModel.getSearchGitHubUser(searchUSer.value)
        } else {
            gitHubViewModel.getSearchGitHubUserDb(searchUSer.value)
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        SearchBarView(text = { searchUSer.value = it }) {
            when (gitHubUser) {
                is UIState.Error -> ErrorCompose(gitHubUser.message)
                is UIState.Loading -> LoadingCompose()
                is UIState.NoDataFound -> NoDataFoundCompose(gitHubUser.message)
                is UIState.Success -> {
                    val user = gitHubUser.data
                    SwipeRefresh(
                        rememberSwipeRefreshState(refreshing.value),
                        onRefresh = { gitHubViewModel.getSearchGitHubUserFollower(searchUSer.value.toString()) }
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                shape = CardDefaults.shape,
                                elevation = CardDefaults.elevatedCardElevation(10.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(user.avatar_url),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(150.dp)
                                            .padding(8.dp)
                                            .clip(CircleShape)
                                    )
                                    Column(
                                        verticalArrangement = Arrangement.Top,
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Spacer(Modifier.height(8.dp))
                                        Text(
                                            user.login.toString(),
                                            style = TextStyle(
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )
                                        Spacer(Modifier.height(8.dp))
                                        Text(
                                            user.name.toString(),
                                            style = TextStyle(
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )
                                        Spacer(Modifier.height(8.dp))
                                        if (user.bio != null) {
                                            Text(
                                                user.bio.toString(),
                                                style = TextStyle(
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                modifier = Modifier.padding(horizontal = 8.dp)
                                            )
                                        }
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.Top,
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        ) {
                                            FollowerAndFollowing(
                                                user.followers.toString(),
                                                "Followers"
                                            ) {
                                                gitHubViewModel.getSearchGitHubUserFollower(
                                                    searchUSer.value.toString()
                                                )
                                                remNavController.navigate(
                                                    "${NavRoute.Follower.route}/${searchUSer}"
                                                )
                                            }
                                            Spacer(Modifier.padding(8.dp))
                                            FollowerAndFollowing(
                                                user.following.toString(),
                                                "Following"
                                            ) {
                                                gitHubViewModel.getSearchGitHubUserFollowing(
                                                    searchUSer.value.toString()
                                                )

                                                remNavController.navigate(
                                                    "${NavRoute.Following.route}/${searchUSer}"
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
