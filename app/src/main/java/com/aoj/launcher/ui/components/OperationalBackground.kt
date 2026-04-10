package com.aoj.launcher.ui.components
                alpha = 0.14f,
                modifier = Modifier.fillMaxSize()
            )
        }

        if (showWatermark) {
            Image(
                painter = painterResource(id = R.drawable.aoj_logo_watermark),
                contentDescription = null,
                alpha = 0.10f,
                alignment = Alignment.TopEnd,
                modifier = Modifier.fillMaxSize()
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            drawCircle(
                color = TacticalKhaki.copy(alpha = 0.04f),
                radius = h * 0.22f,
                center = Offset(w * 0.88f, h * 0.18f),
                style = Stroke(width = 3f)
            )

            drawCircle(
                color = TacticalLine.copy(alpha = 0.06f),
                radius = h * 0.28f,
                center = Offset(w * 0.86f, h * 0.16f),
                style = Stroke(width = 2f)
            )

            drawLine(
                color = TacticalLine.copy(alpha = 0.08f),
                start = Offset(w * 0.58f, 0f),
                end = Offset(w, h * 0.42f),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )

            drawRect(
                color = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.08f),
                topLeft = Offset(w * 0.62f, h * 0.02f),
                size = Size(w * 0.32f, h * 0.30f),
                style = Stroke(width = 1f)
            )

            drawLine(
                color = TacticalKhaki.copy(alpha = 0.06f),
                start = Offset(w * 0.68f, h * 0.04f),
                end = Offset(w * 0.92f, h * 0.28f),
                strokeWidth = 1.5f
            )
        }
    }
}
