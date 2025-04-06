package com.example.speer_rinku_android_assesment.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.speer_rinku_android_assesment.model.GitHubUser
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ErrorCompose(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center

    ) {
        Text(text = message)
    }
}


@Composable
fun LoadingCompose() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center

    ) {
        CircularProgressIndicator()
    }

}


@Composable
fun NoDataFoundCompose(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message)
    }

}

@Composable
fun RefreshLayout(
    refreshing: Boolean,
    refreshLayout: Unit,
    listLayout: @Composable (() -> Unit),
) {
    SwipeRefresh(
        rememberSwipeRefreshState(refreshing),
        onRefresh = { refreshLayout }
    ) {
        listLayout()
    }
}


@Composable

fun FollowerAndFollowing(userTypeValue: String, userType: String, navigate: () -> Unit) {
    Column {
        Text(
            userTypeValue,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ), modifier = Modifier.clickable {
                navigate()
            }
        )
        Text(
            userType,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ), modifier = Modifier.clickable {
                navigate()
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarView( text: (String) -> Unit, item: @Composable (() -> Unit)) {
    var isSearch = rememberSaveable { mutableStateOf<Boolean>(false) }
    var searchUSer = rememberSaveable { mutableStateOf("") }
    SearchBar(query = searchUSer.value,
        onQueryChange = {
            searchUSer.value = it
            text(searchUSer.value.toString())
        },
        modifier = Modifier
            .padding()
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        placeholder = { Text(text = "Search Github users") },
        onSearch = {
            isSearch.value = true
        },
        active = isSearch.value,
        onActiveChange = { isSearch.value = true },
        leadingIcon = {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable(onClick = {})
            )
        },
        trailingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier.clickable(onClick = {
                    isSearch.value = true
                })
            )
        }) {
        item()
    }
}

@Composable
fun FollowerAndFollowingList(followingList: List<GitHubUser>) {
    LazyColumn {
        items(followingList) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(8.dp)
            ) {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    shape = CardDefaults.shape,
                    elevation = CardDefaults.elevatedCardElevation(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(it.avatar_url),
                            contentDescription = "",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(8.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                        Text(
                            it.login.toString(),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                    }
                }

            }
        }
    }
}