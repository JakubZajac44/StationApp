package com.jakub.zajac.station.stations.domain.use_case

import android.location.Location
import javax.inject.Inject

class MeasureDistanceUseCase @Inject constructor() {

     operator fun invoke(sourceLat: Double, sourceLon: Double, destinationLat: Double, destinationLon: Double ): Float {
         val sourceLocation = Location("sourceLocation")

         sourceLocation.latitude = sourceLat
         sourceLocation.longitude = sourceLon

         val destinationLocation = Location("destinationLocation")

         destinationLocation.latitude = destinationLat
         destinationLocation.longitude = destinationLon

         return sourceLocation.distanceTo(destinationLocation) / 1000
    }
}