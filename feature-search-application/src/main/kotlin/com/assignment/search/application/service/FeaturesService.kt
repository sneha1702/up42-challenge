package com.assignment.search.application.service

import com.assignment.search.application.domain.FeaturesData
import com.assignment.search.application.domain.ResultFeaturesData
import org.springframework.stereotype.Service

@Service
class FeaturesService(val featuresComponent: FeaturesComponent) {
    fun mapToResultSearchData(r: FeaturesData) =
            ResultFeaturesData(id = r.properties.id,
                    timestamp = r.properties.timestamp,
                    beginViewingDate = r.properties.acquisition.beginViewingDate,
                    endViewingDate = r.properties.acquisition.endViewingDate,
                    missionName = r.properties.acquisition.missionName)


    fun getAll(): List<ResultFeaturesData> {
        return featuresComponent.getFeaturesRawData()
                .flatMap { e -> e.features.map { r -> mapToResultSearchData(r) } }
    }

    fun getOne(featureId: String): ResultFeaturesData? {
        return featuresComponent.getFeaturesRawData()
                .flatMap { e -> e.features }
                .filter { r -> r.properties.id == featureId }
                .map { r -> mapToResultSearchData(r) }
                .firstOrNull()
    }


    fun getQuicklook(featureId: String): String? {
        return featuresComponent.getFeaturesRawData()
                .flatMap { e -> e.features }
                .filter { r -> r.properties.id == featureId }
                .map { r -> r.properties.quicklook }
                .firstOrNull()
    }
}
