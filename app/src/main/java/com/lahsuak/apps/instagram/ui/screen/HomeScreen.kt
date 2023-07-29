package com.lahsuak.apps.instagram.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lahsuak.apps.instagram.R
import com.lahsuak.apps.instagram.util.WindowSize
import com.lahsuak.apps.instagram.util.WindowType

@Composable
fun HomeScreen(windowSize: WindowSize) {
    val list =
        listOf(
            Data("Home", stringResource(id = R.string.large), Icons.Default.Home),
            Data("Back", stringResource(id = R.string.large), Icons.Default.ArrowBack),
            Data("Search", stringResource(id = R.string.large), Icons.Default.Search),
            Data("Lock", stringResource(id = R.string.large), Icons.Default.Lock),
            Data("Add circle", stringResource(id = R.string.large), Icons.Default.AddCircle),
            Data("Settings", stringResource(id = R.string.large), Icons.Default.Settings),
            Data("Search", stringResource(id = R.string.large), Icons.Default.Search),
            Data("Lock", stringResource(id = R.string.large), Icons.Default.Lock),
            Data("Add circle", stringResource(id = R.string.large), Icons.Default.AddCircle),
            Data("Settings", stringResource(id = R.string.large), Icons.Default.Settings),
            Data("Search", stringResource(id = R.string.large), Icons.Default.Search),
            Data("Lock", stringResource(id = R.string.large), Icons.Default.Lock),
            Data("Add circle", stringResource(id = R.string.large), Icons.Default.AddCircle),
            Data("Settings", stringResource(id = R.string.large), Icons.Default.Settings),
            Data("Search", stringResource(id = R.string.large), Icons.Default.Search),
            Data("Lock", stringResource(id = R.string.large), Icons.Default.Lock),
            Data("Add circle", stringResource(id = R.string.large), Icons.Default.AddCircle),
            Data("Settings", stringResource(id = R.string.large), Icons.Default.Settings),
            Data("Search", stringResource(id = R.string.large), Icons.Default.Search),
            Data("Lock", stringResource(id = R.string.large), Icons.Default.Lock),
            Data("Add circle", stringResource(id = R.string.large), Icons.Default.AddCircle),
            Data("Settings", stringResource(id = R.string.large), Icons.Default.Settings),
            Data("Search", stringResource(id = R.string.large), Icons.Default.Search),
            Data("Lock", stringResource(id = R.string.large), Icons.Default.Lock),
            Data("Add circle", stringResource(id = R.string.large), Icons.Default.AddCircle),
            Data("Settings", stringResource(id = R.string.large), Icons.Default.Settings),
        )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column {
            Text("Items")
            LazyColumn {
                items(list) {
                    DataItem(it, windowSize)
                }
            }
        }
    }
}

@Composable
fun DataItem(data: Data, windowSize: WindowSize) {
    val maxLines by remember(key1 = windowSize) {
        mutableIntStateOf(if (windowSize.width == WindowType.Compat) 4 else 10)
    }
    Row(Modifier.padding(vertical = 4.dp)) {
        Icon(
            imageVector = data.icon,
            contentDescription = data.title
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(data.title, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(data.desc, fontSize = 18.sp)
        }
    }
}

class Data(
    val title: String,
    val desc: String,
    val icon: ImageVector
)