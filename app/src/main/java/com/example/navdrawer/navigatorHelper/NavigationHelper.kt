package com.example.navdrawer.navigatorHelper

import androidx.navigation.NavHostController


object AllDestinations {
    const val EMAIL = "email"
    const val EMAILATTACHMENT = "EmailAttachment"
    const val ADDRESS = "address"
    const val PERSONALINFO = "personalInfo"
}
fun navigateToDestination(destination:String,navController: NavHostController){
    navController.navigate(destination)
}
