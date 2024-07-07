package com.libreforge.calendar.data.utils

import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

val ioExecutor: ExecutorService = Executors.newCachedThreadPool()

fun <T> buildRespResult(r: Response<T?>) = if (r.isSuccessful) {
    Result.success(r.body())
} else {
    Result.failure(Exception(r.message(), Throwable(r.code().toString())))
}