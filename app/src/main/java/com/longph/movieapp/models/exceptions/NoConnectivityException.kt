package com.longph.movieapp.models.exceptions

import java.io.IOException

class NoConnectivityException() : IOException() {

    override val message: String?
        get() = "Unable to contact the server"
}