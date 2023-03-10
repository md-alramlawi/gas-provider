package com.alramlawi.gasprovider.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alramlawi.gasprovider.R

@Composable
fun TodayStatisticsSection(
    totalAmount: Double,
    totalCash: Double,
    totalRemaining: Double
) {

    Surface(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        elevation = 3.dp,
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ItemSection(
                title = stringResource(id = R.string.home_total_amount),
                value = totalAmount.toString(),
                unit = stringResource(id = R.string.kg)
            )

            ItemSection(
                title = stringResource(id = R.string.home_total_cash),
                value = totalCash.toString(),
                unit = stringResource(id = R.string.nis)
            )

            ItemSection(
                title = stringResource(id = R.string.home_total_remaining),
                value = totalRemaining.toString(),
                unit = stringResource(id = R.string.nis)
            )
        }
    }
}

@Composable
fun ItemSection(
    title: String,
    value: String,
    unit: String
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            color = MaterialTheme.colors.onBackground.copy(0.7f),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle1
        )

        Text(
            text = value,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = unit,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground.copy(0.6f)
        )


    }
}


@Preview
@Composable
fun PreviewTodayStatisticsSection() {
    TodayStatisticsSection(totalAmount = 115.02, totalCash = 3005.6, totalRemaining = 120.5)
}