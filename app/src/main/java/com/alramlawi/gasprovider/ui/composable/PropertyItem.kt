package com.alramlawi.gasprovider.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alramlawi.gasprovider.R

@Composable
fun PropertyItem(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onBackground,
    icon: Int? = null,
    title: String? = null,
    content: String? = null
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        icon?.let {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = it),
                contentDescription = title,
                tint = color.copy(alpha = 0.5f)
            )
        }

        title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.h6,
                color = color,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }

        content?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.subtitle1,
                color = color
            )
        }
    }
}


@Preview
@Composable
fun PreviewPropertyItem() {
    PropertyItem(
        icon = R.drawable.ic_person,
        title = "Mohammed"
    )
}