package com.longph.movieapp.models

class ApiThrowable : Throwable {

    var errorCode: Int
    var errorMessage: String

    constructor(errorCode: Int, errorMessage: String) {
        this.errorCode = errorCode
        this.errorMessage = errorMessage
    }
}