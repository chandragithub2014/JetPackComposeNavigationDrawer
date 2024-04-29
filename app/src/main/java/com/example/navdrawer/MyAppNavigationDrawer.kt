package com.example.navdrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.navdrawer.navigatorHelper.AllDestinations
import com.example.navdrawer.screens.AddressComposable
import com.example.navdrawer.screens.AttachEmailComposable
import com.example.navdrawer.screens.EmailComposable
import com.example.navdrawer.screens.PersonalComposable
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color
//https://github.com/saqib-github-commits/JetpackComposeDrawerNavigation/blob/main/app/src/main/java/com/jetpackcompose/navigation/MainNavigation.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationDrawer(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(modifier = modifier) {
                NavigationHeader(modifier)
                Spacer(modifier = Modifier.padding(5.dp))
                NavigationContent{route ->
                    scope.launch {
                        drawerState.close()
                    }
                    navController.navigate(route)
                }

            }
        }, modifier = modifier,
        drawerState = drawerState
    ) {
        NavHost(navController = navController, startDestination = AllDestinations.EMAIL) {
            composable(AllDestinations.EMAIL) {
               EmailComposable(drawerState)

            }
            composable(AllDestinations.EMAILATTACHMENT) {
                AttachEmailComposable(drawerState)
            }

            composable(AllDestinations.ADDRESS) {
                AddressComposable(drawerState)
            }

            composable(AllDestinations.PERSONALINFO) {
                PersonalComposable(drawerState)
            }

        }

    }
}

@Composable
fun getResourceIdByName(drawableName: String): Int {
    // Assume you are using the context to get the resources
    return LocalContext.current.resources.getIdentifier(
        drawableName,
        "drawable",
        LocalContext.current.packageName
    )
}

@Composable
fun NavigationHeader(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {

        Image(
            painterResource(id = R.drawable.ic_android_black_24dp),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(70.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,

            )
    }
}

@Composable
fun NavigationContent( navigationItems: List<NavigationItem> = navigationItemList(), onMenuClick: (String) -> Unit){
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    navigationItems.forEachIndexed { index, item ->
        Text(text = item.title)
        item.children.forEachIndexed { index, childItem ->
            val resourceId = getResourceIdByName(childItem.icon)
            NavigationDrawerItem(
                label = {
                    Text(text = childItem.childName)
                },
                selected = false ,//index == selectedItemIndex,
                onClick = {
               //     selectedItemIndex = index
                    /*navigateToDestination(childItem.destination,navController)
                     scope.launch {
                         drawerState.close()
                     }*/
                    onMenuClick(childItem.destination)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = resourceId),
                        contentDescription = ""
                    )
                }
            )
        }
        Divider(color = Color.Black, thickness = 1.dp)
       // selectedItemIndex += 1
    }
}

fun navigationItemList() = listOf<NavigationItem>(
    NavigationItem(
        "Inbox", listOf(
            ChildItem("ic_email_24", "Email",AllDestinations.EMAIL),
            ChildItem("ic_attach_email_24", "Attached Email",AllDestinations.EMAILATTACHMENT),
        )
    ),
    NavigationItem(
        "Personal", listOf(
            ChildItem("ic_email_24", "Address",AllDestinations.ADDRESS),
            ChildItem("ic_attach_email_24", "Personal Info",AllDestinations.PERSONALINFO),
        )
    )

)

/*
@Composable
fun Navigator(navHostController: NavHostController,modifier: Modifier) {
    NavHost(navController = navHostController, startDestination = AllDestinations.Email) {
        composable(AllDestinations.Email) { EmailComposable() }
        composable(AllDestinations.EmailAttachment) { AttachEmailComposable() }
    }
}*/
