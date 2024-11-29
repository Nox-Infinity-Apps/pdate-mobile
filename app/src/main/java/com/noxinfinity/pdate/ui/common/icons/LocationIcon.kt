import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val LocationIcon: ImageVector
    get() {
        if (_Share_location != null) {
            return _Share_location!!
        }
        _Share_location = ImageVector.Builder(
            name = "Share_location",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(519f, 878f)
                verticalLineToRelative(-80f)
                quadToRelative(42f, -6f, 81.5f, -23f)
                reflectiveQuadToRelative(74.5f, -43f)
                lineToRelative(58f, 58f)
                quadToRelative(-47f, 37f, -101f, 59.5f)
                reflectiveQuadTo(519f, 878f)
                moveToRelative(270f, -146f)
                lineToRelative(-56f, -56f)
                quadToRelative(26f, -33f, 42f, -72.5f)
                reflectiveQuadToRelative(22f, -83.5f)
                horizontalLineToRelative(82f)
                quadToRelative(-8f, 62f, -30.5f, 115.5f)
                reflectiveQuadTo(789f, 732f)
                moveToRelative(8f, -292f)
                quadToRelative(-6f, -45f, -22f, -84.5f)
                reflectiveQuadTo(733f, 284f)
                lineToRelative(56f, -56f)
                quadToRelative(38f, 44f, 61.5f, 98f)
                reflectiveQuadTo(879f, 440f)
                close()
                moveTo(439f, 878f)
                quadToRelative(-153f, -18f, -255.5f, -131f)
                reflectiveQuadTo(81f, 480f)
                quadToRelative(0f, -155f, 102.5f, -268f)
                reflectiveQuadTo(439f, 82f)
                verticalLineToRelative(80f)
                quadToRelative(-120f, 17f, -199f, 107f)
                reflectiveQuadToRelative(-79f, 211f)
                reflectiveQuadToRelative(79f, 210.5f)
                reflectiveQuadTo(439f, 798f)
                close()
                moveToRelative(238f, -650f)
                quadToRelative(-36f, -27f, -76f, -44f)
                reflectiveQuadToRelative(-82f, -22f)
                verticalLineToRelative(-80f)
                quadToRelative(59f, 5f, 113f, 27.5f)
                reflectiveQuadTo(733f, 170f)
                close()
                moveTo(480f, 680f)
                quadToRelative(-58f, -49f, -109f, -105f)
                reflectiveQuadToRelative(-51f, -131f)
                quadToRelative(0f, -68f, 46.5f, -116f)
                reflectiveQuadTo(480f, 280f)
                reflectiveQuadToRelative(113.5f, 48f)
                reflectiveQuadTo(640f, 444f)
                quadToRelative(0f, 75f, -51f, 131f)
                reflectiveQuadTo(480f, 680f)
                moveToRelative(0f, -200f)
                quadToRelative(18f, 0f, 30.5f, -12.5f)
                reflectiveQuadTo(523f, 437f)
                quadToRelative(0f, -17f, -12.5f, -30f)
                reflectiveQuadTo(480f, 394f)
                reflectiveQuadToRelative(-30.5f, 13f)
                reflectiveQuadToRelative(-12.5f, 30f)
                quadToRelative(0f, 18f, 12.5f, 30.5f)
                reflectiveQuadTo(480f, 480f)
            }
        }.build()
        return _Share_location!!
    }

private var _Share_location: ImageVector? = null
