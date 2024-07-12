package com.jakub.zajac.station.stations.domain.use_case

import com.jakub.zajac.station.stations.domain.model.StationDictionaryModel
import com.jakub.zajac.station.stations.domain.model.StationModel
import kotlinx.coroutines.delay
import javax.inject.Inject

class FilterStationUseCase @Inject constructor(
    val normalizeStringUseCase: NormalizeStringUseCase
) {

    suspend operator fun invoke(
        query: String,
        stationDictionaryList: List<StationDictionaryModel>,
        stationList: List<StationModel>
    ): List<StationModel> {
        delay(500)
        return if (query.isBlank()) emptyList()
        else {
            val stationDictionaryListFiltered = stationDictionaryList.filter {
                normalizeStringUseCase(it.keyword).contains(
                    normalizeStringUseCase(query), ignoreCase = true
                )
            }.map { it.stationId }
            stationList.filter { it.id in stationDictionaryListFiltered }
                .sortedByDescending { it.hits }
        }
    }
}