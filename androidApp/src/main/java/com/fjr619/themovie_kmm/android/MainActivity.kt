package com.fjr619.themovie_kmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fjr619.themovie_kmm.android.ui.screens.MovieScreen
import com.fjr619.themovie_kmm.data.source.cache.AppPreferences
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    val appPreferences: AppPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "movie-list") {
                        composable("movie-list") {
                            MovieScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GreetingView(appPreferences: AppPreferences, text: String) {

    val a by appPreferences.getData<Boolean>("Test", false).collectAsState(initial = false)
    val scope = rememberCoroutineScope()
    Row {
        Text(text = "$text ${a}")

        Button(onClick = {
            scope.launch {
                appPreferences.putData("Test", true)
            }
        }) {
            Text(text = "true")
        }

        Button(onClick = {
            scope.launch {
                appPreferences.putData("Test", false)
            }
        }) {
            Text(text = "false")
        }
    }


}

//@Preview
//@Composable
//fun DefaultPreview() {
//    MyApplicationTheme {
//        GreetingView("Hello, Android!")
//    }
//}
