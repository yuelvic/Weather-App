package com.homecredit.weather.presenter.data

// TODO: high: We have Kotlin at our disposal. Use sealed classes. Something like:
//  ```
//  sealed class Resource<out T> {
//      data class Success<T>(val data: T) : Resource<T>()
//      data class Error(val exception: Exception) : Resource<Nothing>()
//      object Loading : Resource<Nothing>()
//  }
//  ```
open class Resource<out T> constructor(val status: ResourceState, val data: T?, val message: String?) {

    // TODO: medium: These methods are never used since they are members of the class. They should be static in companion object.
    fun <T> success(data: T): Resource<T> {
        return Resource(
                ResourceState.SUCCESS,
                data,
                null
        )
    }

    fun <T> error(message: String, data: T?): Resource<T> {
        return Resource(
                ResourceState.ERROR,
                null,
                message
        )
    }

    // TODO: low: Could be defined as `fun loading(): Resource<Nothing>`
    fun <T> loading(): Resource<T> {
        return Resource(
                ResourceState.LOADING,
                null,
                null
        )
    }

}