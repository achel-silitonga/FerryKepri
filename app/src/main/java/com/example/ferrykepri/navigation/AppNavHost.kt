package com.example.ferrykepri.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ferrykepri.data.rememberNetworkStatus
import com.example.ferrykepri.ui.screens.DetailScreen
import com.example.ferrykepri.ui.screens.FavoritesScreen
import com.example.ferrykepri.ui.screens.HomeScreen
import com.example.ferrykepri.ui.screens.LoginScreen
import com.example.ferrykepri.ui.screens.OfflineScreen
import com.example.ferrykepri.ui.screens.ResultsScreen
import com.example.ferrykepri.ui.screens.SplashScreen
import com.example.ferrykepri.ui.screens.TicketsScreen
import com.example.ferrykepri.ui.theme.FerryBlue700
import com.example.ferrykepri.ui.theme.FerryMuted
import com.example.ferrykepri.ui.theme.FerrySky100
import com.example.ferrykepri.ui.theme.FerrySky50
import com.example.ferrykepri.ui.theme.FerryWhite
import com.example.ferrykepri.viewmodel.FerryViewModel

@Composable
fun FerryApp() {
    val navController = rememberNavController()
    val viewModel: FerryViewModel = viewModel()
    val networkStatus = rememberNetworkStatus()
    val snackbarHostState = remember { SnackbarHostState() }
    var offlineDismissed by rememberSaveable { mutableStateOf(false) }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    LaunchedEffect(Unit) {
        viewModel.messages.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(networkStatus.isOnline) {
        if (networkStatus.isOnline) offlineDismissed = false
    }

    fun navigateTab(route: String) {
        if (currentRoute == route) return
        navController.navigate(route) {
            popUpTo(Routes.HOME) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    Box(Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = FerrySky50,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                val showBottomBar = currentRoute == Routes.HOME ||
                    currentRoute == Routes.FAVORITES ||
                    currentRoute == Routes.TICKETS
                if (showBottomBar) {
                    FerryBottomBar(
                        currentRoute = currentRoute,
                        onNavigate = ::navigateTab
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Routes.SPLASH,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(Routes.SPLASH) {
                    SplashScreen(
                        onFinished = {
                            navController.navigate(Routes.LOGIN) {
                                popUpTo(Routes.SPLASH) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Routes.LOGIN) {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate(Routes.HOME) {
                                popUpTo(Routes.LOGIN) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Routes.HOME) {
                    HomeScreen(
                        viewModel = viewModel,
                        onSearch = {
                            navController.navigate(Routes.RESULTS) {
                                launchSingleTop = true
                            }
                        },
                        onOpenDetail = { scheduleId ->
                            navController.navigate(Routes.detail(scheduleId))
                        }
                    )
                }

                composable(Routes.RESULTS) {
                    ResultsScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() },
                        onOpenDetail = { scheduleId ->
                            navController.navigate(Routes.detail(scheduleId))
                        }
                    )
                }

                composable(
                    route = Routes.DETAIL,
                    arguments = listOf(
                        navArgument("scheduleId") { type = NavType.IntType }
                    )
                ) { entry ->
                    DetailScreen(
                        viewModel = viewModel,
                        scheduleId = entry.arguments?.getInt("scheduleId") ?: 0,
                        onBack = { navController.popBackStack() },
                        onBooked = {
                            navController.navigate(Routes.TICKETS) {
                                popUpTo(Routes.HOME)
                                launchSingleTop = true
                            }
                        }
                    )
                }

                composable(Routes.FAVORITES) {
                    FavoritesScreen(
                        viewModel = viewModel,
                        onOpenDetail = { scheduleId ->
                            navController.navigate(Routes.detail(scheduleId))
                        },
                        onExplore = { navigateTab(Routes.HOME) }
                    )
                }

                composable(Routes.TICKETS) {
                    TicketsScreen(
                        viewModel = viewModel,
                        onExplore = { navigateTab(Routes.HOME) }
                    )
                }
            }
        }

        if (!networkStatus.isOnline &&
            !offlineDismissed &&
            currentRoute != Routes.SPLASH
        ) {
            OfflineScreen(
                onRetry = { networkStatus.retry() },
                onOpenTickets = {
                    offlineDismissed = true
                    navigateTab(Routes.TICKETS)
                }
            )
        }
    }
}

@Composable
private fun FerryBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = FerryWhite,
        tonalElevation = 6.dp
    ) {
        BottomBarItem(
            label = "Beranda",
            icon = Icons.Filled.Home,
            selected = currentRoute == Routes.HOME,
            onClick = { onNavigate(Routes.HOME) }
        )
        BottomBarItem(
            label = "Favorit",
            icon = Icons.Filled.Favorite,
            selected = currentRoute == Routes.FAVORITES,
            onClick = { onNavigate(Routes.FAVORITES) }
        )
        BottomBarItem(
            label = "Tiket",
            icon = Icons.Filled.ConfirmationNumber,
            selected = currentRoute == Routes.TICKETS,
            onClick = { onNavigate(Routes.TICKETS) }
        )
    }
}

@Composable
private fun RowScope.BottomBarItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label
            )
        },
        label = { Text(label) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = FerryBlue700,
            selectedTextColor = FerryBlue700,
            indicatorColor = FerrySky100,
            unselectedIconColor = FerryMuted,
            unselectedTextColor = FerryMuted
        )
    )
}
