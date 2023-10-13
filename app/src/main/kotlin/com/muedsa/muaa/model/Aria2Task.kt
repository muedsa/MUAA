package com.muedsa.muaa.model

import kotlinx.serialization.Serializable

@Serializable
data class Aria2Task(
    val gid: String,
    val status: String,
    val dir: String = "",
    val connections: String = "0",
    val downloadSpeed: String = "0",
    val completedLength: String = "0",
    val totalLength: String = "0",
    val files: List<Aria2File> = emptyList()
) {
    val title: String
        get() {
            return if (files.isNotEmpty()) {
                var basePath =
                    if (WIN_DRIVE_LETTER_REGEX.matches(dir)) dir.replaceFirst(
                        ":\\\\",
                        ":/"
                    ) else dir
                basePath = basePath.replace("\\", "/")
                if (!basePath.endsWith("/")) {
                    basePath = "$basePath/"
                }
                files[0].path.removePrefix(basePath).split("/")[0]
            } else gid
        }

    val progress: Float
        get() {
            val completed = completedLength.toLongOrNull() ?: 0
            val total = totalLength.toLongOrNull() ?: 0
            return if (completed > 0 && total > 0) completed.toFloat() / total else 0f
        }

    companion object {
        val WIN_DRIVE_LETTER_REGEX = Regex("^[A-Z]:\\\\")
    }
}
