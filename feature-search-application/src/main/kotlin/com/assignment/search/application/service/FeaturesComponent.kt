package com.assignment.search.application.service

import com.assignment.search.application.domain.SearchData
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component
import java.util.*

@Component
class FeaturesComponent {
    var rawData: List<SearchData> = Collections.emptyList()

    fun getFeaturesRawData(): List<SearchData> {
        if (rawData.size == 0)
            rawData = readRawJsonData();
        return rawData;
    }


    private fun readRawJsonData(): List<SearchData> {
        val mapper = jacksonObjectMapper()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        val myList: List<SearchData> = mapper.readValue(readJsonData())
        return myList
    }

    private fun readJsonData(): String {
        val fileContent = FeaturesComponent::class.java.getResource("/source-data.json").readText()
        return fileContent
    }
}
