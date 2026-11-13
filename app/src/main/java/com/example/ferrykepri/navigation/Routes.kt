package com.example.ferrykepri.navigation

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val HOME = "home"
    const val RESULTS = "results"
    const val DETAIL = "detail/{scheduleId}"
    const val FAVORITES = "favorites"
    const val TICKETS = "tickets"
    const val OFFLINE = "offline"

    fun detail(scheduleId: Int): String = "detail/$scheduleId"
}
