package com.shashavs.domain.model

sealed class AppError : Throwable() {
    class ServerError : AppError()
    class NetworkError : AppError()
    class UnknownError(val msg: String? = null) : AppError()
}