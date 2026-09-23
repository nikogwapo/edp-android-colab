package com.liceo.account.data.network.dto

import kotlinx.serialization.Serializable

// What the SERVER sends. We do not trust it, so every field may be missing.
// TODO 1a: put the @Serializable annotation on the line above the class
@Serializable
data class UserDto(
    // TODO 1b: id — a String? with the default value null
    val id: String? = null,
    // TODO 1c: fullname, email, password, birthdate — each a String? = null
    val fullname: String? = null,
    val email: String? = null,
    val password: String? = null,
    val birthdate: String? = null
)

// What WE send when creating an account. We made it, so nothing is missing.
// TODO 1d: put the @Serializable annotation on the line above the class
@Serializable
data class NewUserDto(
    // TODO 1e: fullname, email, password, birthdate — each a plain String
    val fullname: String,
    val email: String,
    val password: String,
    val birthdate: String
)
