package com.tamersarioglu.pulltorefresh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tamersarioglu.pulltorefresh.composables.CustomPullToRefreshBox
import com.tamersarioglu.pulltorefresh.composables.PersonCard
import com.tamersarioglu.pulltorefresh.ui.Person
import com.tamersarioglu.pulltorefresh.ui.listOfPersons
import com.tamersarioglu.pulltorefresh.ui.theme.PullToRefreshTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PullToRefreshTheme {
                PullToRefreshPerson()
            }
        }
    }
}

@Composable
@Preview
@OptIn(ExperimentalMaterial3Api::class)
fun PullToRefreshPerson() {
    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

    // This is a fake refresh action that simulates a network request
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            delay(1500)
            // Add a random person to the list
            val randomPerson = Person(
                name = "Person ${listOfPersons.size + 1}",
                age = (20..60).random(),
                personImage = R.drawable.person_place_holder
            )
            listOfPersons.add(index = 0, element = randomPerson)
            isRefreshing = false
        }
    }

    // The UI
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Person") },
            )
        },
        floatingActionButton = {
            IconButton(onClick = onRefresh) {
                Icon(Icons.Filled.Refresh, "Trigger Refresh")
            }
        }
    ) { contentpadding ->
        CustomPullToRefreshBox(
            list = listOfPersons,
            modifier = Modifier.padding(contentpadding),
            state = state,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
        )
    }
}

