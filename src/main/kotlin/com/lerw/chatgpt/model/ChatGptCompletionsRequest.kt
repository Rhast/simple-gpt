package com.lerw.chatgpt.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatGptCompletionsRequest(
    val messages: List<Message>,
    val model: String = "gpt-3.5-turbo",
    @get:JsonProperty("max_tokens") val maxTokens: Int? = null,
) {

    data class Message(val role: String, val content: List<Content>) {
        sealed interface Content {
            val type: String

            data class Text(val text: String) : Content {
                override val type = "text"
            }

            data class Image(@get:JsonProperty("image_url") val imageUrl: String) : Content {
                override val type = "image"
            }
        }
    }

    companion object {
        fun singlePrompt(prompt: String) = ChatGptCompletionsRequest(
            messages = listOf(userMessage(Message.Content.Text(prompt))),
        )

        fun singlePromptWithImage(prompt: String, imageUrl: String, maxTokens: Int?) = ChatGptCompletionsRequest(
            model = "gpt-4-vision-preview",
            messages = listOf(
                userMessage(
                    Message.Content.Image(imageUrl),
                    Message.Content.Text(prompt)
                ),
            ),
            maxTokens = maxTokens,
        )
    }
}

private fun userMessage(vararg content: ChatGptCompletionsRequest.Message.Content) = userMessage(content.toList())
private fun userMessage(content: List<ChatGptCompletionsRequest.Message.Content>) =
    ChatGptCompletionsRequest.Message("user", content)


