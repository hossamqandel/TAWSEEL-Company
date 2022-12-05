package dev.hossam.tawseelcompany.feature_home.presentation

data class HomeState(
    val photoUrl: String = "",
    val name: String = "",
    val address: String = "",
    val orderNumber: String = "",
    val orderStatus: String = "",
    val location: String = "",
    val time: String = "",
    val cost: String = "",
)
