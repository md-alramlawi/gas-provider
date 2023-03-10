package com.alramlawi.gasprovider.ui.screen.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alramlawi.gasprovider.R
import com.alramlawi.gasprovider.ui.composable.LogoItem


@Composable
fun SettingScreen(
    navController: NavController,
) {

    SettingScreenContent()
}


@Composable
fun SettingScreenContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){

        LogoItem(tint = MaterialTheme.colors.primary)
    }

}


@Preview
@Composable
fun PreviewSettingContent() {
    SettingScreenContent()
}





