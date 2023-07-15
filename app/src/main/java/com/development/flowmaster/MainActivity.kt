package com.development.flowmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationDrawerItem as M3DrawerItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.development.flowmaster.presentation.app_start.AppStartScreen
import com.development.flowmaster.presentation.flow_counter.FlowCounterScreen
import com.development.flowmaster.presentation.state_flow_and_share_flow.StateFlowAndShareFlowScreen
import com.development.flowmaster.ui.theme.FlowMasterTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowMasterTheme {
                val navController = rememberNavController()
                val drawerState = DrawerState(DrawerValue.Closed)

                ModalDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContent(navController = navController, drawerState = drawerState)
                    }) {

                    NavigationContent(navController = navController, drawerState = drawerState)
                }


            }
        }
    }
}


@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState) {

    ModalDrawerSheet(
        drawerShape = RectangleShape,
        drawerContainerColor = MaterialTheme.colors.surface
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(color = MaterialTheme.colors.primaryVariant)
        ) {
            Text(
                text = "Flow Master",
                letterSpacing = 5.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        M3DrawerItemWrapper(
            label = "Flow Counter",
            route = Routes.FlowCounterScreenRoute,
            drawerState = drawerState,
            navController = navController
        )

        Spacer(modifier = Modifier.height(8.dp))
        M3DrawerItemWrapper(
            label = "App Start",
            route = Routes.AppStartScreenRoute,
            drawerState = drawerState,
            navController = navController
        )

        Spacer(modifier = Modifier.height(8.dp))
        M3DrawerItemWrapper(
            label = "State Flow & Shared Flow",
            route = Routes.StateFlowAndShareFlowRoute,
            drawerState = drawerState,
            navController = navController
        )
    }


}


@Composable
fun NavigationContent(navController: NavHostController, drawerState: DrawerState) {
    NavHost(
        navController = navController,
        startDestination = Routes.FlowCounterScreenRoute,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }) {
        composable(route = Routes.FlowCounterScreenRoute) {
            FlowCounterScreen(navController = navController, drawerState = drawerState)
        }
        composable(route = Routes.AppStartScreenRoute) {
            AppStartScreen(navController = navController, drawerState = drawerState)
        }
        composable(route = Routes.StateFlowAndShareFlowRoute) {
            StateFlowAndShareFlowScreen(navController = navController, drawerState = drawerState)
        }
    }
}

@Composable
fun M3DrawerItemWrapper(
    label: String,
    route: String,
    drawerState: DrawerState,
    navController: NavController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scope = rememberCoroutineScope()

    M3DrawerItem(
        label = { Text(text = label) },
        selected = currentDestination?.route == route,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp),
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colors.primary.copy(alpha = 0.5f),
            unselectedContainerColor = MaterialTheme.colors.surface
        ),
        onClick = {
            scope.launch {
                drawerState.close()
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        })

}