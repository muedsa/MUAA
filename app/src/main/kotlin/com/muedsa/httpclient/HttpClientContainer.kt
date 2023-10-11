package com.muedsa.httpclient

class HttpClientContainer {
    companion object {
        const val HEADER_KEY_USER_AGENT = "User-Agent"
        const val HEADER_KEY_COOKIE = "Cookie"
        const val HEADER_KEY_ACCEPT_ENCODING = "Accept-Encoding"
        const val HEADER_KEY_CONTENT_ENCODING = "Content-Encoding"
        const val HEADER_KEY_REFERER = "Referer"

        const val HEADER_KEY_HOST = "Host"

        const val HEADER_VALUE_USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36"

        const val HEADER_VALUE_PART_ENCODING_IDENTITY = "identity"
        const val HEADER_VALUE_PART_ENCODING_AES128GCM = "aes128gcm"
        const val HEADER_VALUE_PART_ENCODING_BR = "br"
        const val HEADER_VALUE_PART_ENCODING_COMPRESS = "compress"
        const val HEADER_VALUE_PART_ENCODING_DEFLATE = "deflate"
        const val HEADER_VALUE_PART_ENCODING_GZIP = "gzip"
        const val HEADER_VALUE_PART_ENCODING_PACK200_GZIP = "pack200-gzip"
        const val HEADER_VALUE_PART_ENCODING_ZSTD = "zstd"
    }

}