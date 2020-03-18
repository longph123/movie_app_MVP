package com.longph.movieapp.models.responses

data class RestResponse<T : Any>(
        var results: T,
        var page: Int,
        var total_results: Long,
        var total_pages: Long,
        var status_code: Int,
        var status_message: String,
        var success: Boolean = true
)
