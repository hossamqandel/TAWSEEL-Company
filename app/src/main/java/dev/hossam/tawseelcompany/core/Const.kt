package com.hossam.tawsel.core

import java.text.SimpleDateFormat

object Const {

    val BASE_URL by lazy { "https://www.tawseleg.com/api/company/" }
    val Exception_MESSAGE_IO by lazy { "Couldn't reach server. Check your internet connection" }
    val Exception_MESSAGE_HTTP by lazy { "An expected error occurred" }
    val ORDER_STARTED by lazy { "Order has started" }
    val ORDER_COMPLETED by lazy { "Order completed" }
    val ORDER_CANCELED by lazy { "The order has been cancelled" }
    val MESSAGE_UNAUTHORIZED by lazy { "Unauthorized, Please try to login again" }
    private const val DATE_TIME_FORMAT = "hh:mm a"
    val sdf = SimpleDateFormat(DATE_TIME_FORMAT)



    const val LANGUAGE_ARABIC = "ar"
    const val LANGUAGE_ENGLISH = "en"
    val ONE_SECOND: Long by lazy { 1000 }
    val NULL_TEXT_FORM by lazy { "The order has been cancelled" }



}