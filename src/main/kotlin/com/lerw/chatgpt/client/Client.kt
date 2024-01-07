package com.lerw.chatgpt.client

import com.lerw.chatgpt.localImageUrl
import com.lerw.chatgpt.model.ChatGptCompletionsRequest
import com.lerw.chatgpt.model.ChatGptCompletionsResponse
import com.lerw.chatgpt.model.ModelResponse
import com.lerw.chatgpt.model.singleResponse
import com.lerw.chatgpt.json.configureObjectMapper
import com.lerw.chatgpt.file.readFromFirstExisting
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.serialization.jackson.jackson

// TODO: proper config for different environments
private val openApiKey = readFromFirstExisting(
    "./secrets/open-api.key",
    "/etc/kana-converter/open-api.key",
)

private val client = HttpClient(CIO) {
    expectSuccess = true
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.BODY
    }
    install(ContentNegotiation) {
        jackson {
            configureObjectMapper()
        }
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 60_000
    }
    defaultRequest {
        url("https://api.openai.com/")
        header("Content-Type", "application/json")
        header("Authorization", "Bearer $openApiKey")
    }
}

suspend fun askChatGpt(prompt: String): String {
    val response: ChatGptCompletionsResponse = client.post("v1/chat/completions") {
        setBody(ChatGptCompletionsRequest.singlePrompt(prompt))
    }.body()
    return response.singleResponse()
}

suspend fun askChatGptWithImage(prompt: String, imageUrl: String, maxTokens: Int): String {
    val response: ChatGptCompletionsResponse = client.post("v1/chat/completions") {
        setBody(ChatGptCompletionsRequest.singlePromptWithImage(prompt, imageUrl, maxTokens))
    }.body()
    return response.singleResponse()
}

suspend fun askChatGptWithLocalImage(prompt: String, imagePath: String, maxTokens: Int): String {
    return askChatGptWithImage(prompt, localImageUrl(imagePath), maxTokens)
}

suspend fun listModels(): ModelResponse {
    return client.get("v1/models").body()
}

