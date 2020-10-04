package com.assignment.search.application.domain


data class SearchData(val type: String,
                      val features: List<FeaturesData>)

data class FeaturesData(val type: String, val properties: FeatureProperty)
data class FeatureProperty(val id: String, val timestamp: String, val acquisition: Acquisition, val quicklook: String = "")
data class Acquisition(val beginViewingDate: String, val endViewingDate: String, val missionName: String)

data class ResultFeaturesData(val id: String, val timestamp: String, val beginViewingDate: String, val endViewingDate: String, val missionName: String)