package com.lerw.chatgpt.model

import com.fasterxml.jackson.annotation.JsonProperty

//Data class for this json
//{
//  "object": "list",
//  "data": [
//    {
//      "id": "model-id-0",
//      "object": "model",
//      "created": 1686935002,
//      "owned_by": "organization-owner"
//    },
//    {
//      "id": "model-id-1",
//      "object": "model",
//      "created": 1686935002,
//      "owned_by": "organization-owner",
//    },
//    {
//      "id": "model-id-2",
//      "object": "model",
//      "created": 1686935002,
//      "owned_by": "openai"
//    },
//  ],
//  "object": "list"
//}
data class ModelResponse(
    val `object`: String,
    val data: List<Model>,
) {
    data class Model(
        val id: String,
        val `object`: String,
        val created: Long,
        @get:JsonProperty("owned_by")
        val ownedBy: String,
    )
}