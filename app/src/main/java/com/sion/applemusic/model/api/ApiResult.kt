package com.sion.applemusic.model.api

/**
 * Api狀態
 *
 * [loading]: 等待response
 * [loaded]: api request結束，不管成功失敗
 * [error]: api request error, 附上錯誤資訊
 * [success]: api request success, 附上response data
 */
sealed class ApiResult<T> {
    companion object {
        fun <T> loading(): ApiResult<T> {
            return Loading()
        }

        fun <T> loaded(): ApiResult<T> {
            return Loaded()
        }

        fun <T> error(throwable: Throwable): ApiResult<T> {
            return Error(throwable)
        }

        fun <T> success(result: T?): ApiResult<T> {
            return Success(result)
        }
    }

    data class Success<T>(val result: T?) : ApiResult<T>()

    data class Error<T>(val throwable: Throwable) : ApiResult<T>()

    class Loading<T> : ApiResult<T>()

    class Loaded<T> : ApiResult<T>()
}
