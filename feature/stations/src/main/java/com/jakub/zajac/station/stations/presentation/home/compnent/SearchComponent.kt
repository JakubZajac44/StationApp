package com.jakub.zajac.station.stations.presentation.home.compnent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchComponent(
    modifier: Modifier = Modifier,
    onClickEvent: () -> Unit,
    onClearEvent: () -> Unit,
    defaultHint: String,
    currentSearchValue: String?
) {

    Box(modifier = modifier.clickable {
        onClickEvent.invoke()
    }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = currentSearchValue ?: defaultHint,
                modifier = Modifier.weight(1.0f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.width(10.dp))

            Icon(
                modifier = Modifier.clickable {
                    onClearEvent.invoke()
                },
                imageVector = Icons.Default.Clear,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun SearchComponentPreviewNoSearchValue() {
    SearchComponent(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
        onClickEvent = {},
        onClearEvent = {},
        defaultHint = "Wybierze stację początkową",
        currentSearchValue = null
    )
}

@Preview
@Composable
fun SearchComponentPreviewSearchValueShort() {
    SearchComponent(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
        onClickEvent = {},
        onClearEvent = {},
        defaultHint = "Wybierze stację początkową",
        currentSearchValue = "Testowa krótka stacja"
    )
}

@Preview
@Composable
fun SearchComponentPreviewSearchValueLong() {
    SearchComponent(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
        onClickEvent = {},
        onClearEvent = {},
        defaultHint = "Wybierze stację początkową",
        currentSearchValue = "Bardzo bardzo bardzo bardzo bardzo bardzo bardzo bardzo bardzo testowa stacja"
    )
}