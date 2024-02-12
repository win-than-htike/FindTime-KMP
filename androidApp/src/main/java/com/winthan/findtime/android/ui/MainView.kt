package com.winthan.findtime.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.winthan.findtime.android.MyApplicationTheme

@Composable
fun MainView(
    actionBarFun: topBarFun = { EmptyComposable() }
) {

    val showAddDialog = remember {
        mutableStateOf(false)
    }

    val currentTimezoneStrings = remember {
        SnapshotStateList<String>()
    }

    val selectedIndex = remember {
        mutableIntStateOf(0)
    }

    MyApplicationTheme {

        Scaffold(
            topBar = {
                actionBarFun(selectedIndex.intValue)
            },
            floatingActionButton = {
                if (selectedIndex.value == 0) {
                    FloatingActionButton(
                        onClick = {
                            showAddDialog.value = true
                        },
                        modifier = Modifier.padding(16.dp),
                        shape = FloatingActionButtonDefaults.largeShape,
                        contentColor = MaterialTheme.colorScheme.secondary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Timezone"
                        )
                    }
                }
            },
            bottomBar = {
                NavigationBar(
                    contentColor = MaterialTheme.colorScheme.primary,
                ) {
                    bottomNavigationItems.forEachIndexed { index, bottomNavigationItem ->
                        NavigationBarItem(
                            colors =  NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = Color.White,
                                unselectedIconColor = Color.Black,
                                unselectedTextColor = Color.Black,
                                indicatorColor = MaterialTheme.colorScheme.primary,
                            ),
                            label = {
                                Text(bottomNavigationItem.route, style = MaterialTheme.typography.bodyMedium)
                            },
                            icon = {
                                Icon(
                                    bottomNavigationItem.icon,
                                    contentDescription = bottomNavigationItem.iconContentDescription
                                )
                            },
                            selected = selectedIndex.intValue == index,
                            onClick = {
                                selectedIndex.intValue = index
                            }
                        )
                    }
                }
            },
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {

                if (showAddDialog.value) {
                    AddTimeZoneDialog(
                        onAdd = { newTimezones ->
                            showAddDialog.value = false
                            for (zone in newTimezones) {
                               if (!currentTimezoneStrings.contains(zone)) {
                                   currentTimezoneStrings.add(zone)
                               }
                            }
                        },
                        onDismiss = {
                            showAddDialog.value = false
                        }
                    )
                }

                when (selectedIndex.value) {
                    0 -> TimeZoneScreen(currentTimezoneStrings)
                    else -> FindMeetingScreen(
                        currentTimezoneStrings
                    )
                }
            }
        }

    }

}