package dev.hossam.tawseelcompany.feature_home.data.remote.dto

data class HomeDto(
    val company: Company,
    val last: Last,
    val notifications: Int
)

data class Company(
    val address: String,
    val created_at: String,
    val email: String,
    val id: Int,
    val logo: String,
    val name: String,
    val phone: String
)

data class Last(
    val address: String,
    val address_details: String,
    val city_id: Any,
    val client: String,
    val company_id: Int,
    val created_at: String,
    val date: String,
    val driver_id: Int,
    val end: String,
    val id: Int,
    val items: List<Item>,
    val other_phone: String,
    val paid: Int,
    val payment_type: String,
    val phone: String,
    val reason: Any,
    val shipping: String,
    val start: String,
    val status: String,
    val total: String,
    val updated_at: String
)

data class Item(
    val desc: Any,
    val id: Int,
    val item: String,
    val order_id: Int,
    val price: String,
    val quantity: String,
    val total: String
)