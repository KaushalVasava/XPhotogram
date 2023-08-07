package com.lahsuak.apps.instagram.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberText(number: Int, title: String, fontSize: TextUnit = 16.sp) {
    Row {
        Text(number.toString(), fontWeight = FontWeight.Bold, fontSize = fontSize)
        Spacer(modifier = Modifier.width(4.dp))
        Text(title, color = Color.Gray, fontWeight = FontWeight.Light)
    }
}