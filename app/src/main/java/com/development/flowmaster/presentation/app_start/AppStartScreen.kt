package com.development.flowmaster.presentation.app_start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.development.flowmaster.presentation.global.DefaultTopBar

@Composable
fun AppStartScreen(navController: NavController, drawerState: DrawerState) {
    Scaffold(
        topBar = {
            DefaultTopBar(
                navController = navController,
                drawerState = drawerState,
                title = "App Start"
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            Text(text = "App Start Screen", modifier = Modifier.align(Alignment.Center))
        }

    }
}