package com.muedsa.httpclient

import java.net.URL
import java.net.URLConnection
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class SimpleHttpClient {

    private fun connect(url: String): URLConnection {
        val urlObj = URL(url)
        val urlConnection = urlObj.openConnection()
        urlConnection.setConnectTimeout(2000)
        urlConnection.setReadTimeout(3000)
        return urlConnection
    }

    fun get(url: String): String {
        val connect = connect(url)
        val coding = connect.getHeaderField(HttpClientContainer.HEADER_KEY_CONTENT_ENCODING)
        return convertStreamToString(connect.getInputStream(), coding)
    }

    fun post(url: String, data: ByteArray?): String {
        val connect = connect(url)
        connect.setDoOutput(true)
        connect.getOutputStream().write(data)
        val coding = connect.getHeaderField(HttpClientContainer.HEADER_KEY_CONTENT_ENCODING)
        return convertStreamToString(connect.getInputStream(), coding)
    }

    fun post(url: String, params: Map<String, Any>): String {
        val postData = StringBuilder()
        for ((key, value) in params) {
            if (postData.isNotEmpty()) {
                postData.append('&')
            }
            postData.append(URLEncoder.encode(key, StandardCharsets.UTF_8.name()))
                .append("=")
                .append(URLEncoder.encode(value.toString(), StandardCharsets.UTF_8.name()))
        }
        return post(url, postData.toString().toByteArray(StandardCharsets.UTF_8))
    }
}