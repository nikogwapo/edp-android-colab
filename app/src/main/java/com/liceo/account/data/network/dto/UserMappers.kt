package com.liceo.account.data.network.dto

import com.liceo.account.domain.model.User

// Turns the server's messy shape into the app's clean shape.
fun UserDto.toDomain(): User = User(
    // TODO 3a: id — use id, or "" when it is null
    id = id ?: "",
    // TODO 3b: fullName — fullname with spaces trimmed, or "(no name)" when null
    fullName = fullname?.trim() ?: "(no name)",
    // TODO 3c: email — email with spaces trimmed, or "" when null
    email = email?.trim() ?: "",
    // TODO 3d: birthdate — birthdate, or "(not set)" when null
    birthdate = birthdate ?: "(not set)"
    // Notice: the password is NOT copied. The screen never needs it.
)
