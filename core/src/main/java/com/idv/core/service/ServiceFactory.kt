package com.idv.core.service

interface ServiceFactory {
    fun <T> make(baseURL: String, serviceClass: Class<T>): T
}