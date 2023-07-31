package com.lahsuak.apps.instagram.models

sealed class ApiFailure {
    data class Unknown(val error: String) : ApiFailure()
}
