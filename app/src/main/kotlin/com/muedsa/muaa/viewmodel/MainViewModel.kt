package com.muedsa.muaa.viewmodel

import android.util.Base64
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muedsa.httpclient.SimpleHttpClient
import com.muedsa.muaa.KEY_LINK_FILE_URL
import com.muedsa.muaa.KEY_RPC_TOKEN
import com.muedsa.muaa.KEY_RPC_URL
import com.muedsa.muaa.repo.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject internal constructor(
    private val dataStoreRepo: DataStoreRepo
) : ViewModel() {

    private val httpClient = SimpleHttpClient()

    val rpcUrlState = mutableStateOf("not init")
    val rpcTokenState = mutableStateOf("not init")
    val linkFileUrlState = mutableStateOf("not init")
    val rpcResultState = mutableStateOf("")

    fun aria2TellActive() {
        rpcResultState.value = "wait response..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = """
                    {
                        "id": 233,
                        "jsonrpc": "2.0",
                        "method": "aria2.tellActive",
                        "params": [
                            "token:${rpcTokenState.value}",
                            ["guid", "status", "completedLength", "totalLength", "files"]
                        ]
                    }
                """.trimIndent()
                Timber.d("rpc request:\n$request")
                rpcResultState.value =
                    httpClient.post(rpcUrlState.value, request.encodeToByteArray())
            } catch (t: Throwable) {
                rpcResultState.value = t.stackTraceToString()
                Timber.d(t)
            }
        }
    }

    fun aria2AddUriFromFileUrl() {
        rpcResultState.value = "wait response..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val linkList = fetchLinkList()
                if (linkList.isNotEmpty()) {
                    rpcResultState.value = aria2AddUris(linkList)
                } else {
                    rpcResultState.value = "link list is empty"
                }
            } catch (t: Throwable) {
                rpcResultState.value = t.stackTraceToString()
                Timber.d(t)
            }
        }
    }

    private fun fetchLinkList(): List<String> {
        val linkBase64List = httpClient.get(linkFileUrlState.value).lines()
        return linkBase64List.filter { it.isNotBlank() }
            .map { Base64.decode(it, Base64.DEFAULT).decodeToString() }
    }

    private fun aria2AddUris(linkList: List<String>): String {
        val request = linkList.mapIndexed { index, link ->
            """
                {
                    "id": ${233 + index},
                    "jsonrpc": "2.0",
                    "method": "aria2.addUri",
                    "params": [
                        "token:${rpcTokenState.value}",
                        ["$link"]
                    ]
                }
            """.trimIndent()
        }.joinToString(",", "[", "]")
        Timber.d("rpc request:\n$request")
        return httpClient.post(rpcUrlState.value, request.encodeToByteArray())
    }

    fun saveSetting() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.putString(KEY_RPC_URL, rpcUrlState.value)
            dataStoreRepo.putString(KEY_RPC_TOKEN, rpcTokenState.value)
            dataStoreRepo.putString(KEY_LINK_FILE_URL, linkFileUrlState.value)
            rpcResultState.value = "Saved!"
        }
    }

    init {
        viewModelScope.launch {
            dataStoreRepo.collectPrefs {
                Timber.d("collectPrefs")
                rpcUrlState.value = it[KEY_RPC_URL] ?: "http://localhost:6800/jsonrpc"
                rpcTokenState.value = it[KEY_RPC_TOKEN] ?: "2333"
                linkFileUrlState.value = it[KEY_LINK_FILE_URL]
                    ?: "https://raw.githubusercontent.com/muedsa/MUAA/download/example.txt"
            }
        }
    }
}