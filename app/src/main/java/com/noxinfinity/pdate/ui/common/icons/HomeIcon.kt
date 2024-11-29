/*
* Converted using https://composables.com/svgtocompose
*/

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val HomeIcon: ImageVector
    get() {
        if (_Icons8Home != null) {
            return _Icons8Home!!
        }
        _Icons8Home = ImageVector.Builder(
            name = "Icons8Home",
            defaultWidth = 48.dp,
            defaultHeight = 48.dp,
            viewportWidth = 48f,
            viewportHeight = 48f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 3f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 10f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(13.4f, 13.8f)
                lineToRelative(-3.6f, 2.9f)
                curveToRelative(-1.40f, 1.10f, -2.30f, 2.90f, -2.30f, 4.70f)
                verticalLineToRelative(18.1f)
                curveToRelative(00f, 1.10f, 0.90f, 20f, 20f, 20f)
                horizontalLineToRelative(20f)
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 3f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 10f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(35f, 41.5f)
                horizontalLineToRelative(3.5f)
                curveToRelative(1.10f, 00f, 20f, -0.90f, 20f, -20f)
                verticalLineTo(21.4f)
                curveToRelative(00f, -1.80f, -0.80f, -3.60f, -2.30f, -4.70f)
                lineTo(24.6f, 6f)
                curveToRelative(-0.40f, -0.30f, -0.90f, -0.30f, -1.20f, 00f)
                lineToRelative(-4.6f, 3.6f)
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 3f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 10f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(18.5f, 35.7f)
                verticalLineToRelative(-8.2f)
                curveToRelative(00f, -0.60f, 0.40f, -10f, 10f, -10f)
                horizontalLineToRelative(8.9f)
                curveToRelative(0.60f, 00f, 10f, 0.40f, 10f, 10f)
                verticalLineToRelative(14f)
            }
        }.build()
        return _Icons8Home!!
    }

private var _Icons8Home: ImageVector? = null
