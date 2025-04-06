package com.example.speer_rinku_android_assesment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.speer_rinku_android_assesment.base.RouteNavigation
import com.example.speer_rinku_android_assesment.base.ViewModelProvider
import com.example.speer_rinku_android_assesment.compose.GitHubUserSearch
import com.example.speer_rinku_android_assesment.db.GitHubRoomDatabase
import com.example.speer_rinku_android_assesment.di.GitHubApplication
import com.example.speer_rinku_android_assesment.model.GitHubUser
import com.example.speer_rinku_android_assesment.ui.theme.Speer_rinku_android_assesmentTheme
import com.example.speer_rinku_android_assesment.viewModel.GitHubViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelProvider: ViewModelProvider
    lateinit var gitHubViewModel: GitHubViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as GitHubApplication).applicationComponent.inject(this)

        gitHubViewModel = androidx.lifecycle.ViewModelProvider(
            this, viewModelProvider
        )[GitHubViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            RouteNavigation(gitHubViewModel)
        }
    }
}

// check internet connection
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}