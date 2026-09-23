package com.liceo.account.data.network

import com.liceo.account.data.network.dto.NewUserDto
import com.liceo.account.data.network.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {
    // TODO 4a: GET "users" with a @Query("email") parameter named email.
    // Call the function findByEmail. It returns List<UserDto>.
    @GET("users")
    suspend fun findByEmail(@Query("email") email: String): List<UserDto>

    // TODO 4b: POST "users" with a @Body parameter named user of type NewUserDto.
    // Call the function createUser. It returns UserDto.
    @POST("users")
    suspend fun createUser(@Body user: NewUserDto): UserDto
}
