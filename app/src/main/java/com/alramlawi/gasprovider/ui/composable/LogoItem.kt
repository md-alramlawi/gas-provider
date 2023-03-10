package com.alramlawi.gasprovider.ui.composable

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alramlawi.gasprovider.R

@Composable
fun LogoItem(
    modifier: Modifier = Modifier,
    tint: Color,

) {
    Icon(
        modifier = modifier.size(200.dp),
        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
        contentDescription = "",
        tint = tint
    )
}