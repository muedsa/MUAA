package com.muedsa.muaa.model

import kotlinx.serialization.Serializable

@Serializable
data class Aria2File(
    val index: String,
    val path: String,
    val completedLength: String = "0",
    val length: String = "0",
    val selected: String = "true"
)
