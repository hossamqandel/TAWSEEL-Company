package dev.hossam.tawseelcompany.core

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import dev.hossam.tawseelcompany.R
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

    const val JSON_KEY_MESSAGE = "message"

    const val ALL = "all"
    const val COMPLETED = "completed"
    const val REFUSED = "refused"
    const val STARTED = "started"
    const val IN_STOCK = "in stock"
    const val CANCELLED = "cancelled"

    const val STATUS_COLOR_COMPLETED = "#15C975"
    const val STATUS_COLOR_IN_STOCK = "#15C975"
    const val STATUS_COLOR_STARTED = "#FF6600"
    const val STATUS_COLOR_REFUSED = "#EB2323"
    const val STATUS_COLOR_CANCELLED = "#EB2323"

    const val RESTAURANT_PIC = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/McDonald%27s_SVG_logo.svg/2095px-McDonald%27s_SVG_logo.svg.png"
    const val DRIVER_PIC = "https://media.istockphoto.com/photos/m-on-my-way-picture-id635967404?b=1&k=20&m=635967404&s=170667a&w=0&h=Y0q6o_u351FikOUk2td8qOcEcrUDaAaf3DHzHzCWnok="
    const val RESTAURANT_FAKE_NAME = "McDonald's"

    const val LANGUAGE_ARABIC = "ar"
    const val LANGUAGE_ENGLISH = "en"
    val ONE_SECOND: Long by lazy { 1000 }
    val NULL_TEXT_FORM by lazy { "The order has been cancelled" }



}