package com.noxinfinity.pdate.ui.common.components

import android.graphics.Bitmap
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.core.graphics.drawable.toBitmap
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.noxinfinity.pdate.R

@Composable
fun MapBoxMap(
	modifier: Modifier = Modifier,
	point: Point?,
		) {
	val context = LocalContext.current

	val marker = remember(context) {
		val originalMarker = context.getDrawable(R.drawable.ic_heart)!!.toBitmap()
		Bitmap.createScaledBitmap(originalMarker, 64, 64, false)
	}
	var pointAnnotationManager: PointAnnotationManager? by remember {
		mutableStateOf(null)
	}
	AndroidView(
			factory = {
					MapView(it).also { mapView ->
					mapView.getMapboxMap().loadStyleUri(Style.TRAFFIC_DAY)
					val annotationApi = mapView.annotations
					pointAnnotationManager = annotationApi.createPointAnnotationManager()
			}
			},
			update = { mapView ->
	if (point != null) {
		pointAnnotationManager?.let {
			it.deleteAll()
			val pointAnnotationOptions = PointAnnotationOptions()
					.withPoint(point)
					.withIconImage(marker)

			it.create(pointAnnotationOptions)
			mapView.getMapboxMap()
					.flyTo(CameraOptions.Builder().zoom(16.0).center(point).build())

		}
	}
	NoOpUpdate
        },
	modifier = modifier
    )
}