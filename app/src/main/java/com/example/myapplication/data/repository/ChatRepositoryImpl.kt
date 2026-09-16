package com.example.myapplication.data.repository

import com.example.myapplication.core.AppResult
import com.example.myapplication.data.network.ChatApiService
import com.example.myapplication.data.network.dto.NewMessageDto
import com.example.myapplication.data.network.dto.toDomain
import com.example.myapplication.domain.ChatRepository
import com.example.myapplication.domain.Message
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ChatRepositoryImpl(
    private val api: ChatApiService
) : ChatRepository {
    override suspend fun getMessages(): AppResult<List<Message>> = safeCall {
        api.getMessages().toDomain()
    }

    override suspend fun sendMessage(sender: String, text: String): AppResult<Unit> = safeCall {
        val dto = NewMessageDto(sender, text, System.currentTimeMillis())
        api.sendMessage(dto)
        Unit
    }

    private inline fun <T> safeCall(block: () -> T): AppResult<T> =
        try {
            AppResult.Success(block())
        } catch (e: UnknownHostException) {
            AppResult.Failure.NoInternet
        } catch (e: SocketTimeoutException) {
            AppResult.Failure.Timeout
        } catch (e: HttpException) {
            // Handle MockAPI "Max number of elements reached" error
            val errorBody = e.response()?.errorBody()?.string()
            if (errorBody?.contains("Max number of elements") == true) {
                AppResult.Failure.Unknown("Server is full. Please try again later.")
            } else {
                AppResult.Failure.Unknown("Server error: ${e.code()}")
            }
        } catch (e: IOException) {
            AppResult.Failure.NoInternet
        } catch (e: Exception) {
            AppResult.Failure.Unknown(e.message)
        }
}
