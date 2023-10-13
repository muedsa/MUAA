package com.muedsa.muaa.features

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import com.muedsa.muaa.ui.CustomerColor
import com.muedsa.muaa.viewmodel.MainViewModel

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun Aria2RpcWidget(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel()
) {
    var rpcUrl by remember { mainViewModel.rpcUrlState }
    var rpcToken by remember { mainViewModel.rpcTokenState }
    var linkFileUrl by remember { mainViewModel.linkFileUrlState }

    TvLazyColumn(modifier = modifier) {

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
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            Button(onClick = {
                mainViewModel.aria2AddUriFromFileUrl()
            }) {
                Text(text = "aria2.addUri From URL")
            }
            Spacer(modifier = Modifier.height(10.dp))
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
            Spacer(modifier = Modifier.height(10.dp))
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
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            Row {
                Button(onClick = {
                    mainViewModel.saveSetting()
                }) {
                    Text(text = "Save Setting")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    mainViewModel.aria2TellActive()
                }) {
                    Text(text = "aria2.tellActive")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            Row {
                Button(onClick = {
                    mainViewModel.aria2PauseAll()
                }) {
                    Text(text = "aria2.pauseAll")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    mainViewModel.aria2UnpauseAll()
                }) {
                    Text(text = "aria2.unpauseAll")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            Row {
                Button(onClick = {
                    mainViewModel.aria2PurgeDownloadResult()
                }) {
                    Text(text = "aria2.purgeDownloadResult")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    mainViewModel.aria2SaveSession()
                }) {
                    Text(text = "aria2.saveSession")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}