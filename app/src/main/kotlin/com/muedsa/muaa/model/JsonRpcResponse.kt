package com.muedsa.muaa.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonRpcResponse<T>(
    val id: Int,
    @SerialName("jsonrpc")
    val jsonRpc: String,
    val result: T?
)
