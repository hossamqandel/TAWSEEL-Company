package dev.hossam.tawseelcompany.core

import okio.IOException
import retrofit2.HttpException

object ResponseErrorType {


    const val IO_EXCEPTION = "0"
    const val HTTP_EXCEPTION_UNAUTHORIZED = "1"
    const val HTTP_EXCEPTION = "2"
    const val HTTP_EXCEPTION_LOGIN = "3"
    const val HTTP_EXCEPTION_INVALID_DATA_INPUT = "4"
}