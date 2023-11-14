package com.muedsa.muaa.viewmodel

import android.util.Base64
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muedsa.httpclient.SimpleHttpClient
import com.muedsa.muaa.KEY_LINK_FILE_URL
import com.muedsa.muaa.KEY_RPC_TOKEN
import com.muedsa.muaa.KEY_RPC_URL
import com.muedsa.muaa.model.AppSetting
import com.muedsa.muaa.model.Aria2Task
import com.muedsa.muaa.model.JsonRpcResponse
import com.muedsa.muaa.model.LazyData
import com.muedsa.muaa.repo.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject internal constructor(
    private val dataStoreRepo: DataStoreRepo
) : ViewModel() {

    private val httpClient = SimpleHttpClient()

    val appSettingLDSF = dataStoreRepo.dataStore.data.map { prefs ->
        AppSetting(
            rpcUrl = prefs[KEY_RPC_URL] ?: "http://localhost:6800/jsonrpc",
            rpcToken = prefs[KEY_RPC_TOKEN] ?: "2333",
            linkFileUrl = prefs[KEY_LINK_FILE_URL]
                ?: "https://raw.githubusercontent.com/muedsa/MUAA/download/example.txt"
        ).let { model ->
            LazyData.success(model)
        }
    }.catch {
        Timber.d(it)
        emit(LazyData.fail(it))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LazyData.init()
    )

    private val _rpcResultSF = MutableStateFlow("")
    val rpcResultSF: StateFlow<String> = _rpcResultSF
    private val _aria2TaskListSF = MutableStateFlow<List<Aria2Task>>(emptyList())
    val aria2TaskListSF: StateFlow<List<Aria2Task>> = _aria2TaskListSF

    fun aria2TellActive(rpcUrl: String, rpcToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _rpcResultSF.value = LOADING_RESULT
            try {
                val request = """
                    {
                        "id": 233,
                        "jsonrpc": "2.0",
                        "method": "aria2.tellActive",
                        "params": [
                            "token:$rpcToken",
                            [
                                "gid", 
                                "status", 
                                "dir", 
                                "connections", 
                                "downloadSpeed", 
                                "completedLength", 
                                "totalLength", 
                                "files"
                            ]
                        ]
                    }
                """.trimIndent()
                Timber.d("rpc request:\n$request")
                val rpcResult = httpClient.post(rpcUrl, request.encodeToByteArray())
                Timber.d("rpc response:\n$rpcResult")
                val response = JSON.decodeFromString<JsonRpcResponse<List<Aria2Task>>>(rpcResult)
                if (response.result.isNullOrEmpty()) {
                    _rpcResultSF.value = rpcResult
                } else {
                    _rpcResultSF.value = ""
                    _aria2TaskListSF.value = response.result
                }
            } catch (t: Throwable) {
                _rpcResultSF.value = t.stackTraceToString()
                Timber.d(t)
            }
        }
    }

    fun aria2AddUriFromFileUrl(rpcUrl: String, rpcToken: String, fileUrl: String) {
        viewModelScope.launch {
            _rpcResultSF.value = LOADING_RESULT
            _rpcResultSF.value = withContext(Dispatchers.IO) {
                val linkList = fetchLinkList(fileUrl)
                if (linkList.isNotEmpty()) {
                    aria2AddUris(rpcUrl, rpcToken, linkList)
                } else {
                    "link list is empty"
                }
            }
        }
    }

    private fun fetchLinkList(url: String): List<String> {
        val linkBase64List = httpClient.get(url).lines()
        return linkBase64List.filter { it.isNotBlank() }
            .map { Base64.decode(it, Base64.DEFAULT).decodeToString() }
    }

    private fun aria2AddUris(rpcUrl: String, rpcToken: String, linkList: List<String>): String {
        val request = linkList.mapIndexed { index, link ->
            """
                {
                    "id": ${233 + index},
                    "jsonrpc": "2.0",
                    "method": "aria2.addUri",
                    "params": [
                        "token:$rpcToken",
                        ["$link"]
                    ]
                }
            """.trimIndent()
        }.joinToString(",", "[", "]")
        Timber.d("rpc request:\n$request")
        return httpClient.post(rpcUrl, request.encodeToByteArray())
    }

    fun aria2PauseAll(rpcUrl: String, rpcToken: String) {
        viewModelScope.launch {
            _rpcResultSF.value = LOADING_RESULT
            _rpcResultSF.value = withContext(Dispatchers.IO) {
                val request = """
                    {
                        "id": 233,
                        "jsonrpc": "2.0",
                        "method": "aria2.pauseAll",
                        "params": [
                            "token:$rpcToken"
                        ]
                    }
                """.trimIndent()
                rpcPost(request, rpcUrl)
            }
        }
    }

    fun aria2UnpauseAll(rpcUrl: String, rpcToken: String) {
        viewModelScope.launch {
            _rpcResultSF.value = LOADING_RESULT
            _rpcResultSF.value = withContext(Dispatchers.IO) {
                val request = """
                    {
                        "id": 233,
                        "jsonrpc": "2.0",
                        "method": "aria2.unpauseAll",
                        "params": [
                            "token:$rpcToken"
                        ]
                    }
                """.trimIndent()
                rpcPost(request, rpcUrl)
            }
        }
    }

    fun aria2PurgeDownloadResult(rpcUrl: String, rpcToken: String) {
        viewModelScope.launch {
            _rpcResultSF.value = LOADING_RESULT
            _rpcResultSF.value = withContext(Dispatchers.IO) {
                val request = """
                    {
                        "id": 233,
                        "jsonrpc": "2.0",
                        "method": "aria2.purgeDownloadResult",
                        "params": [
                            "token:$rpcToken"
                        ]
                    }
                """.trimIndent()
                rpcPost(request, rpcUrl)
            }
        }
    }

    fun aria2SaveSession(rpcUrl: String, rpcToken: String) {
        viewModelScope.launch {
            _rpcResultSF.value = LOADING_RESULT
            _rpcResultSF.value = withContext(Dispatchers.IO) {
                val request = """
                    {
                        "id": 233,
                        "jsonrpc": "2.0",
                        "method": "aria2.saveSession",
                        "params": [
                            "token:$rpcToken"
                        ]
                    }
                """.trimIndent()
                rpcPost(request, rpcUrl)
            }
        }
    }

    private suspend fun rpcPost(request: String, url: String): String {
        return try {
            Timber.d("rpc request:\n$request")
            httpClient.post(url, request.encodeToByteArray())
        } catch (t: Throwable) {
            Timber.d(t)
            t.stackTraceToString()
        }
    }


    fun saveSetting(appSetting: AppSetting) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.dataStore.edit {
                it[KEY_RPC_URL] = appSetting.rpcUrl
                it[KEY_RPC_TOKEN] = appSetting.rpcToken
                it[KEY_LINK_FILE_URL] = appSetting.linkFileUrl
            }
            _rpcResultSF.value = "Saved!"
        }
    }

    companion object {
        val JSON = Json { ignoreUnknownKeys = true }
        const val LOADING_RESULT = "wait response..."
    }
}