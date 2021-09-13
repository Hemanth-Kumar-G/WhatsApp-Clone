package com.hemanthdev.whatsappclone.modules.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hemanthdev.whatsappclone.R
import com.hemanthdev.whatsappclone.modules.home.chat.ChatView
import com.hemanthdev.whatsappclone.modules.home.search.SearchView
import com.hemanthdev.whatsappclone.modules.home.status.StatusView
import com.hemanthdev.whatsappclone.ui.theme.LightGreen
import com.hemanthdev.whatsappclone.ui.theme.WhatsAppCloneTheme
import com.hemanthdev.whatsappclone.ui.theme.black15Bold
import com.hemanthdev.whatsappclone.utils.LOGIN_SCREEN
import com.hemanthdev.whatsappclone.utils.REGISTRATION_SCREEN
import com.hemanthdev.whatsappclone.utils.SPLASH_SCREEN

@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun HomeView(profile: () -> Unit) {
    val navController = rememberNavController()
    WhatsAppCloneTheme {

        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopBar {
                        profile()
                    }
                },
                bottomBar = {
                    BottomBar(navController = navController)
                },
                drawerContent = {
                    Text(text = "drawerContent")

                }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {

                    NavHost(navController, startDestination = NavigationItem.Chat.route) {
                        composable(NavigationItem.Chat.route) {
                            ChatView()
                        }
                        composable(NavigationItem.Search.route) {
                            SearchView()
                        }
                        composable(NavigationItem.Status.route) {
                            StatusView()
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun TopBar(
    onProfileClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "title",
                    style = black15Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.9f)
                )
                IconButton(onClick = { onProfileClick() }) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "profile"
                    )
                }
            }
        },
        backgroundColor = Color.White
    )
}


@Composable
fun BottomBar(
    navController: NavHostController
) {
    val items = listOf(
        NavigationItem.Chat,
        NavigationItem.Search,
        NavigationItem.Status,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = Color.White
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(22.dp)
                    )
                },
                unselectedContentColor = Color.Gray,
                selectedContentColor = LightGreen,
                selected = currentRoute == item.route,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                })
        }
    }
}


/**
 * A navigator item class which will be used as a navigation for bottom bar in home screen
 */
sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Chat : NavigationItem(LOGIN_SCREEN, R.mipmap.ic_chat, "chat")
    object Search : NavigationItem(SPLASH_SCREEN, R.mipmap.ic_search, "search")
    object Status : NavigationItem(REGISTRATION_SCREEN, R.mipmap.ic_status, "status")
}