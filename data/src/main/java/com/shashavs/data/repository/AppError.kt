package com.shashavs.data.repository

sealed class AppError : Throwable() {
    class ServerError : AppError()
    class NetworkError : AppError()
    class UnknownError(val msg: String? = null) : AppError()
}