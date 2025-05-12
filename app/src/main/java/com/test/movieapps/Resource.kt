package com.test.movieapps


/**
 * A sealed class representing different states of a data resource, which can be one of the following:
 * - [Success]: Data is successfully retrieved with [data] containing the result.
 * - [Error]: An error occurred during data retrieval with [message] providing an error message,
 *   and optionally [data] containing partial or null data.
 * - [Loading]: Data is being fetched, and the result is still in progress.
 *
 * @param <T> The type of data resource.
 * @property data The data associated with the resource. It can be null in case of [Error] state.
 * @property message The error message associated with the [Error] state. It is null in case of [Success] and [Loading] states.
 * @author Samuel Mareno
 */

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    /**
     * Represents a successful data retrieval state.
     *
     * @param data The data retrieved successfully.
     */
    class Success<T>(data: T) : Resource<T>(data)

    /**
     * Represents an error state during data retrieval.
     *
     * @param message The error message providing details about the encountered error.
     * @param data Partial or null data that might be available despite the error.
     */
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    /**
     * Represents the loading state while fetching data.
     * This state is typically used to indicate that data is still being fetched or processed.
     *
     * @param message An optional message providing additional details about the loading state.
     */
    class Loading<T>(message: String? = null, data: T? = null) : Resource<T>(data, message)
}

