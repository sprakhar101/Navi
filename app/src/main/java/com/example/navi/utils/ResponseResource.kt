package com.example.navi.utils

data class ResponseResource<out T>(val status: ResponseStatus, val data: T, val message: String?) {

    companion object {
        fun <T> success(data: T): ResponseResource<T> {
            return ResponseResource(status = ResponseStatus.SUCCESS, data = data, message = null)
        }

        fun <T> error(data: T, message: String?): ResponseResource<T> {
            return ResponseResource(status = ResponseStatus.FAILURE, data = data, message = message)
        }

        fun <T> loading(data: T): ResponseResource<T> {
            return ResponseResource(status = ResponseStatus.LOADING, data = data, message = null)
        }
    }
}