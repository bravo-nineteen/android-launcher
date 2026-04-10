package com.aoj.launcher.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toBitmap

@Composable
fun DrawableIcon(
    drawable: Drawable?,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    val painter = remember(drawable) {
        drawable?.let {
            val bitmap = it.toBitmap(width = 144, height = 144).copy(android.graphics.Bitmap.Config.ARGB_8888, false)
            BitmapPainter(bitmap.asImageBitmap())
        }
    }

    if (painter != null) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = modifier.size(44.dp)
        )
    }
}
