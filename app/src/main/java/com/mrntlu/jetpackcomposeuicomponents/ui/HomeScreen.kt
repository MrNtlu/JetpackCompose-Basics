@file:OptIn(ExperimentalPagerApi::class)

package com.mrntlu.jetpackcomposeuicomponents.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.mrntlu.jetpackcomposeuicomponents.models.TabRowItem
import com.mrntlu.jetpackcomposeuicomponents.viewmodels.SharedViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
) {
    LaunchedEffect(key1 = Unit) {
        sharedViewModel.fabOnClick.value = {
            navController.navigate("add")
        }
    }

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val tabRowItems = listOf(
        TabRowItem(
            title = "Tab 1",
            screen = { TabScreen(text = "Tab 1") },
            icon = Icons.Rounded.Place,
        ),
        TabRowItem(
            title = "Tab 2",
            screen = { TabScreen(text = "Tab 2") },
            icon = Icons.Rounded.Search,
        ),
        TabRowItem(
            title = "Tab 3",
            screen = { TabScreen(text = "Tab 3") },
            icon = Icons.Rounded.Star,
        )
    )

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = MaterialTheme.colors.secondary
                )
            },
        ) {
            tabRowItems.forEachIndexed { index, item ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = "")
                    },
                    text = {
                        Text(
                            text = item.title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                )
            }
        }
        HorizontalPager(
            count = tabRowItems.size,
            state = pagerState,
        ) {
            tabRowItems[pagerState.currentPage].screen()
        }
    }
}