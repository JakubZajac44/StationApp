package com.jakub.zajac.station.stations.domain.use_case

import java.text.Normalizer
import javax.inject.Inject

class NormalizeStringUseCase @Inject constructor() {

    operator fun invoke(
        inputToNormalize: String,
    ): String {
        return Normalizer.normalize(inputToNormalize, Normalizer.Form.NFD)
            .replace("\\p{Mn}+".toRegex(), "")
    }
}



