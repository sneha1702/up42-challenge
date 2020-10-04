package com.assignment.search.application.service

import com.assignment.search.application.domain.FeaturesData
import com.assignment.search.application.domain.ResultFeaturesData
import org.springframework.stereotype.Service

@Service
class FeaturesService {
    fun mapToResultSearchData(r: FeaturesData) =
            ResultFeaturesData(id = r.properties.id,
                    timestamp = r.properties.timestamp,
                    beginViewingDate = r.properties.acquisition.beginViewingDate,
                    endViewingDate = r.properties.acquisition.endViewingDate,
                    missionName = r.properties.acquisition.missionName)


    fun getAll(): List<ResultFeaturesData> {
        return FeaturesComponent.getFeaturesRawData()
                .flatMap { e -> e.features.map { r -> mapToResultSearchData(r) } }.sortedBy { e -> e.id }
    }

    fun getOne(featureId: String): List<ResultFeaturesData> {
        return FeaturesComponent.getFeaturesRawData()
                .flatMap { e -> e.features.map { r -> mapToResultSearchData(r) } }
                .filter { r -> r.id.equals(featureId) }
    }
}
