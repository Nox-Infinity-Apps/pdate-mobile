import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.noxinfinity.pdate.utils.theme.samsungSansFonts

val SamsungSansTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = samsungSansFonts,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = samsungSansFonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = samsungSansFonts,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    titleLarge = TextStyle(
        fontFamily = samsungSansFonts,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    )
)