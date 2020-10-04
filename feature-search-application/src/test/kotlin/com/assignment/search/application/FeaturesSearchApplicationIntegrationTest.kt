package com.assignment.search.application

import com.assignment.search.application.controller.ErrorsDetails
import com.assignment.search.application.domain.ResultFeaturesData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDate


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {


    @Test
    fun `Assert getAll features endpoint`() {
        val responseEntity = restTemplate.exchange("/features", HttpMethod.GET, null,
                object : ParameterizedTypeReference<List<ResultFeaturesData>>() {})
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        val result: List<ResultFeaturesData> = responseEntity.body!!
        assertThat(result.size).isNotEqualTo(0)
        val firstData = result.stream().findFirst().get()
        assertThat(firstData.id).isEqualTo("08a190bf-8c7e-4e94-a22c-7f3be11f642c")
        assertThat(firstData.beginViewingDate).isEqualTo("1555044772083")
        assertThat(firstData.endViewingDate).isEqualTo("1555044797082")
        assertThat(firstData.timestamp).isEqualTo("1555044772083")
        assertThat(firstData.missionName).isEqualTo("Sentinel-1A")

    }


    @Test
    fun `Assert getOne features endpoint`() {
        val responseEntity = restTemplate.exchange("/features/08a190bf-8c7e-4e94-a22c-7f3be11f642c", HttpMethod.GET, null,
                object : ParameterizedTypeReference<ResultFeaturesData>() {})
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        val result: ResultFeaturesData = responseEntity.body!!
        assertThat(result.id).isEqualTo("08a190bf-8c7e-4e94-a22c-7f3be11f642c")
        assertThat(result.beginViewingDate).isEqualTo("1555044772083")
        assertThat(result.endViewingDate).isEqualTo("1555044797082")
        assertThat(result.timestamp).isEqualTo("1555044772083")
        assertThat(result.missionName).isEqualTo("Sentinel-1A")

    }

    @Test
    fun `Assert getOne features endpoint Exception Handling`() {

        val str = "not-existing-value"
        val responseEntity: ResponseEntity<ErrorsDetails> = restTemplate.getForEntity<ErrorsDetails>("/features/$str", ErrorsDetails::class.java)
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(responseEntity.body).isEqualTo(ErrorsDetails(time = LocalDate.now(), message = "Feature Not " +
                "Found!", details = "FeatureId: $str"))

    }

}