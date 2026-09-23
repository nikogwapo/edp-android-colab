package com.liceo.account.data

import com.liceo.account.core.AppResult
import com.liceo.account.data.network.NetworkModule
import com.liceo.account.data.network.UserApiService
import com.liceo.account.data.network.dto.NewUserDto
import com.liceo.account.data.network.dto.UserDto
import com.liceo.account.data.network.dto.toDomain
import com.liceo.account.domain.model.User
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UserRepository(
    private val api: UserApiService = NetworkModule.userApi
) {
    // GIVEN (read it, do not change it)
    // MockAPI may answer 404 instead of [] when a search finds nobody.
    // This turns that 404 into an empty list, so "nobody found" is not an error.
    private suspend fun findUsers(email: String): List<UserDto> =
        try {
            api.findByEmail(email)
        } catch (e: HttpException) {
            if (e.code() == 404) emptyList() else throw e
        }

    // GIVEN (read it, do not change it)
    // Runs your block and turns every network exception into a named Failure.
    // The LAST line of your block is the result that comes back.
    private inline fun <T> safeCall(block: () -> AppResult<T>): AppResult<T> =
        try {
            block()
        } catch (e: UnknownHostException) {
            AppResult.Failure.NoInternet
        } catch (e: SocketTimeoutException) {
            AppResult.Failure.Timeout
        } catch (e: HttpException) {
            AppResult.Failure.Unknown("Server error ${e.code()}")
        } catch (e: SerializationException) {
            AppResult.Failure.Unknown("The server sent data we could not read.")
        } catch (e: IOException) {
            AppResult.Failure.NoInternet
        }

    // TODO 6 Log in (9 points)
    // Finds the user with this email, then checks the password.
    suspend fun login(email: String, password: String): AppResult<User> = safeCall {
        // TODO 6a: search the server: val matches = findUsers(...the trimmed email...)
        val matches = findUsers(email.trim())
        // TODO 6b: val found = the FIRST user in matches whose email equals the typed
        // email (ignore upper/lower case) AND whose password equals password
        val found = matches.firstOrNull {
            it.email?.trim().equals(email.trim(), ignoreCase = true) && it.password == password
        }
        // TODO 6c: LAST line: if found is null -> AppResult.Failure.WrongLogin
        // otherwise -> AppResult.Success(found.toDomain())
        if (found == null) AppResult.Failure.WrongLogin else AppResult.Success(found.toDomain())
    }

    // TODO 7 Create an account (9 points)
    // Creates the account — but only if nobody already uses this email.
    suspend fun register(
        fullName: String,
        email: String,
        password: String,
        birthdate: String
    ): AppResult<User> = safeCall {
        // TODO 7a: val taken = true when ANY user from findUsers(...) has exactly
        // this email (ignore upper/lower case)
        val taken = findUsers(email.trim()).any {
            it.email?.trim().equals(email.trim(), ignoreCase = true)
        }
        // TODO 7b: LAST expression starts: if (taken) -> AppResult.Failure.EmailTaken
        // TODO 7c: else -> build a NewUserDto, send it with api.createUser(...)
        // and return AppResult.Success(saved.toDomain())
        if (taken) {
            AppResult.Failure.EmailTaken
        } else {
            val saved = api.createUser(
                NewUserDto(
                    fullname = fullName.trim(),
                    email = email.trim(),
                    password = password,
                    birthdate = birthdate.trim()
                )
            )
            AppResult.Success(saved.toDomain())
        }
    }
}
