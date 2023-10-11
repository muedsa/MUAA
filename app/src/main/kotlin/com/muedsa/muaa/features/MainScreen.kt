package com.muedsa.muaa.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.muedsa.muaa.viewmodel.MainViewModel


@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel()
) {

    val rpcResult by remember { mainViewModel.rpcResultState }

    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Aria2RpcWidget(
            modifier = Modifier
                .weight(1f)
                .padding(20.dp),
            mainViewModel = mainViewModel
        )

        Divider(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(20.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            text = rpcResult,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}