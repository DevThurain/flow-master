package com.development.flowmaster.presentation.state_flow_and_share_flow

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DrawerState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.development.flowmaster.presentation.flow_counter.FlowCounterViewModel
import com.development.flowmaster.presentation.global.DefaultTopBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun StateFlowAndShareFlowScreen(navController: NavController, drawerState: DrawerState) {
    val viewModel = viewModel<StateFlowAndShareFlowViewModel>()
    val counterState = viewModel.counterStateFlow.collectAsState(initial = 0)

    Scaffold(
        topBar = {
            DefaultTopBar(
                navController = navController,
                drawerState = drawerState,
                title = "State Flow Vs Share Flow"
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Button(onClick = {
                    viewModel.increment()
                }) {
                    Text(text = "State Flow Value Increment : ${counterState.value}")
                }
            }
        }

    }
}

// for native state flow
// after rotated the device, this stateFlow start emit again unlike sharedFlow
// sharedFlow emit only once to subscriber
// they are both hot flow, there is not collector in emitting duration, collector did not get emitted value

fun <T> ComponentActivity.collectLatestLiveCycleFlow(flow: Flow<T>,collect: suspend () -> Unit){
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
            flow.collectLatest {
                collect()
            }
        }
    }
}