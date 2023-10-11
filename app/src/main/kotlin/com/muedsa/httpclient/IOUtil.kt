package com.muedsa.httpclient

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.zip.GZIPInputStream

fun convertStreamToString(inputStream: InputStream, coding: String?): String {
    return if (!coding.isNullOrBlank()) {
        if (HttpClientContainer.HEADER_VALUE_PART_ENCODING_IDENTITY == coding) {
            decodeInputStreamToString(inputStream)
        } else if (HttpClientContainer.HEADER_VALUE_PART_ENCODING_GZIP == coding) {
            decodeInputStreamToString(GZIPInputStream(inputStream))
        } else {
            throw IllegalStateException("coding $coding not supported")
        }
    } else {
        decodeInputStreamToString(inputStream)
    }
}

private fun decodeInputStreamToString(inputStream: InputStream): String {
    return BufferedReader(InputStreamReader(inputStream)).use {
        it.readText()
    }
}