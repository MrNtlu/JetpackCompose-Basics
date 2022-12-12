package com.mrntlu.jetpackcomposeuicomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.mrntlu.jetpackcomposeuicomponents.models.BottomNavItem
import com.mrntlu.jetpackcomposeuicomponents.models.TabRowItem
import com.mrntlu.jetpackcomposeuicomponents.ui.HomeScreen
import com.mrntlu.jetpackcomposeuicomponents.ui.TabScreen
import com.mrntlu.jetpackcomposeuicomponents.ui.theme.JetpackComposeUIComponentsTheme
import com.mrntlu.jetpackcomposeuicomponents.ui.theme.Purple500
import com.mrntlu.jetpackcomposeuicomponents.utils.SearchWidgetState
import com.mrntlu.jetpackcomposeuicomponents.viewmodels.SharedViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeUIComponentsTheme {
                navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    navController: NavHostController,
) {
    val sharedViewModel: SharedViewModel = viewModel()
    var selectedItem by remember { mutableStateOf(0) }

    //Drawer
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    var searchWidgetState by remember{ mutableStateOf(SearchWidgetState.CLOSED) }
    var searchTextState by remember { mutableStateOf("") }

    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Home",
            route = "home",
            icon = Icons.Rounded.Home,
        ),
        BottomNavItem(
            name = "Create",
            route = "add",
            icon = Icons.Rounded.AddCircle,
        ),
        BottomNavItem(
            name = "Settings",
            route = "settings",
            icon = Icons.Rounded.Settings,
        ),
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (searchWidgetState == SearchWidgetState.OPENED) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    elevation = 8.dp,
                    color = colors.primary,
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = searchTextState,
                        onValueChange = {
                            searchTextState = it
                        },
                        placeholder = {
                            Text(
                                modifier = Modifier.alpha(ContentAlpha.medium),
                                text = "Search",
                                color = Color.White
                            )
                        },
                        singleLine = true,
                        maxLines = 1,
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.alpha(ContentAlpha.high),
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "Search Icon",
                                tint = Color.White
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    if (searchTextState.isNotEmpty() || searchTextState.isNotBlank()) {
                                        searchTextState = ""
                                    } else {
                                        searchWidgetState = SearchWidgetState.CLOSED
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = "Close Icon",
                                    tint = Color.White
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions (
                            onSearch = {
                                searchWidgetState = SearchWidgetState.CLOSED
                            }
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            cursorColor = Color.White.copy(alpha = ContentAlpha.medium),
                            textColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                }
            } else {
                TopAppBar(
                    title = {
                        Text(text = "Jetpack Compose")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } }
                        ) {
                            Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Drawer Icon")
                        }
                    },
                    actions = {
                        IconButton(onClick = { searchWidgetState = SearchWidgetState.OPENED }) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "",
                                tint = Color.White,
                            )
                        }
                    },
                )
            }
        },
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(126.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        modifier = Modifier
                            .matchParentSize(),
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "",
                    )

                    Image(
                        modifier = Modifier
                            .scale(1.4f),
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "",
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                bottomNavItems.forEachIndexed { index, item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable {
                                selectedItem = index
                                coroutineScope.launch {
                                    scaffoldState.drawerState.close()
                                }
                                navController.navigate(item.route)
                            },
                        backgroundColor = if (selectedItem == index) Purple500 else Color.White,
                        elevation = 0.dp,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = "${item.name} Icon"
                            )

                            Text(
                                modifier = Modifier
                                    .padding(start = 24.dp),
                                text = item.name,
                            )
                        }
                    }
                }
            }
        },
        drawerGesturesEnabled = true,
        floatingActionButton = {
            AnimatedVisibility (
                visible = navController.currentBackStackEntryAsState().value?.destination?.route != "add",
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                if (navController.currentBackStackEntryAsState().value?.destination?.route == "home") {
                    FloatingActionButton(
                        onClick = {
                            sharedViewModel.fabOnClick.value.invoke()
                        },
                        containerColor = MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add FAB",
                            tint = Color.White,
                        )
                    }
                } else if (navController.currentBackStackEntryAsState().value?.destination?.route == "settings") {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        SmallFloatingActionButton(
                            onClick = { sharedViewModel.smallFabOnClick.value.invoke() },
                            containerColor = colors.secondaryVariant,
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.MyLocation,
                                contentDescription = "Location FAB",
                                tint = Color.White,
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        ExtendedFloatingActionButton(
                            text = {
                                Text(text = "Navigate", color = Color.White)
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Rounded.NearMe,
                                    contentDescription = "Navigate FAB",
                                    tint = Color.White,
                                )
                            },
                            onClick = { sharedViewModel.fabOnClick.value.invoke() },
                            expanded = sharedViewModel.expandedFab.value,
                            containerColor = colors.secondaryVariant,
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
//            androidx.compose.material3.BottomAppBar(
//                containerColor = MaterialTheme.colors.primary,
//                actions = {
//                    IconButton(onClick = { /* Check onClick */ }) {
//                        Icon(Icons.Filled.Check, contentDescription = "", tint = Color.White)
//                    }
//                    IconButton(onClick = { /* Edit onClick */ }) {
//                        Icon(
//                            Icons.Filled.Edit, contentDescription = "", tint = Color.White)
//                    }
//                    IconButton(onClick = { /* Delete onClick */ }) {
//                        Icon(Icons.Filled.Delete, contentDescription = "", tint = Color.White,)
//                    }
//                },
//                floatingActionButton = {
//                    androidx.compose.material3.FloatingActionButton(
//                        onClick = { /* FAB onClick */ },
//                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
//                        elevation = androidx.compose.material3.FloatingActionButtonDefaults.bottomAppBarFabElevation()
//                    ) {
//                        Icon(Icons.Filled.Add, "")
//                    }
//                }
//            )

            androidx.compose.material3.NavigationBar(
                containerColor = MaterialTheme.colors.primary,
            ) {
                bottomNavItems.forEachIndexed { index, item ->

                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item.route)
                        },
                        label = {
                            Text(
                                text = item.name,
                                fontWeight = FontWeight.SemiBold,
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = "${item.name} Icon",
                            )
                        },
                    )
                }
            }
        },
        content = { contentPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                contentAlignment = Alignment.Center,
            ) {
                NavigationComposable(
                    navController = navController,
                    sharedViewModel = sharedViewModel,
                )
            }
        }
    )
}
