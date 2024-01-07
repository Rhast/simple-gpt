package com.lerw.chatgpt.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatGptCompletionsResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val choices: List<Choice>,
    val usage: Usage,
    val model: String,
) {
    data class Choice(
        val index: Int,
        val message: Message,
        @get:JsonProperty("finish_reason")
        val finishReason: String?,
        @get:JsonProperty("finish_details")
        val finishDetails: Any?,
    ) {
        data class Message(
            val role: String,
            val content: String,
        )
    }

    data class Usage(
        @JsonProperty("prompt_tokens")
        val promptTokens: Int,
        @JsonProperty("completion_tokens")
        val completionTokens: Int,
        @JsonProperty("total_tokens")
        val totalTokens: Int,
    )
}

fun ChatGptCompletionsResponse.singleResponse(): String {
    return choices.single().message.content
}
