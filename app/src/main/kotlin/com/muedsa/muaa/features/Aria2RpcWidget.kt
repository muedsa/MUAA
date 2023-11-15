package com.muedsa.muaa.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import com.muedsa.muaa.model.AppSetting
import com.muedsa.muaa.model.LazyType
import com.muedsa.muaa.ui.CustomerColor
import com.muedsa.muaa.viewmodel.MainViewModel

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun Aria2RpcWidget(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel()
) {
    val appSettingLD by mainViewModel.appSettingLDSF.collectAsState()
    if (appSettingLD.type == LazyType.SUCCESS && appSettingLD.data != null) {
        val appSetting = appSettingLD.data!!
        var rpcUrl by remember { mutableStateOf(appSetting.rpcUrl) }
        var rpcToken by remember { mutableStateOf(appSetting.rpcToken) }
        var linkFileUrl by remember { mutableStateOf(appSetting.linkFileUrl) }

        TvLazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        mainViewModel.aria2TellActive(
                            rpcUrl = rpcUrl,
                            rpcToken = rpcToken
                        )
                    }
                ) {
                    Text(text = "aria2.tellActive")
                }
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(), onClick = {
                        mainViewModel.aria2PauseAll(
                            rpcUrl = rpcUrl,
                            rpcToken = rpcToken
                        )
                    }) {
                    Text(text = "aria2.pauseAll")
                }
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(), onClick = {
                        mainViewModel.aria2PurgeDownloadResult(
                            rpcUrl = rpcUrl,
                            rpcToken = rpcToken
                        )
                    }) {
                    Text(text = "aria2.purgeDownloadResult")
                }
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(), onClick = {
                        mainViewModel.aria2SaveSession(
                            rpcUrl = rpcUrl,
                            rpcToken = rpcToken
                        )
                    }) {
                    Text(text = "aria2.saveSession")
                }
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(), onClick = {
                        mainViewModel.aria2UnpauseAll(
                            rpcUrl = rpcUrl,
                            rpcToken = rpcToken
                        )
                    }) {
                    Text(text = "aria2.unpauseAll")
                }
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(), onClick = {
                        mainViewModel.aria2AddUriFromFileUrl(
                            rpcUrl = rpcUrl,
                            rpcToken = rpcToken,
                            fileUrl = linkFileUrl
                        )
                    }) {
                    Text(text = "aria2.addUri From URL below")
                }
            }
            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CustomerColor.outline,
                        cursorColor = MaterialTheme.colorScheme.onSurface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    singleLine = true,
                    value = linkFileUrl,
                    onValueChange = {
                        linkFileUrl = it
                    },
                    label = {
                        Text(text = "MAGNET LINK FILE URL")
                    }
                )

            }

            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CustomerColor.outline,
                        cursorColor = MaterialTheme.colorScheme.onSurface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    singleLine = true,
                    value = rpcUrl,
                    onValueChange = {
                        rpcUrl = it
                    },
                    label = {
                        Text(text = "RPC URL")
                    }
                )
            }

            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CustomerColor.outline,
                        cursorColor = MaterialTheme.colorScheme.onSurface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    singleLine = true,
                    value = rpcToken,
                    onValueChange = {
                        rpcToken = it
                    },
                    label = {
                        Text(text = "RPC TOKEN")
                    }
                )
            }

            item {
                Button(onClick = {
                    mainViewModel.saveSetting(
                        AppSetting(
                            rpcUrl = rpcUrl,
                            rpcToken = rpcToken,
                            linkFileUrl = linkFileUrl
                        )
                    )
                }) {
                    Text(text = "Save Setting")
                }
            }
        }
    } else {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading...(￣o￣) . z Z")
        }
    }
}