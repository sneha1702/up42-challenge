package com.assignment.search.application

import com.assignment.search.application.domain.ResultFeaturesData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.awt.Menu


@Suppress("UNCHECKED_CAST")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {


    @Test
    fun `Assert getAll features endpoint`() {
        val responseEntity = restTemplate.exchange("/features", HttpMethod.GET, null,
                object : ParameterizedTypeReference<List<ResultFeaturesData>>() {})
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        val result: List<ResultFeaturesData> = responseEntity.body!!
        assertThat(result.size).isNotEqualTo(0)
        val firstData = result.stream().findFirst().get();
        assertThat(firstData.id).isEqualTo("08a190bf-8c7e-4e94-a22c-7f3be11f642c")
        assertThat(firstData.beginViewingDate).isEqualTo("1555044772083")
        assertThat(firstData.endViewingDate).isEqualTo("1555044797082")
        assertThat(firstData.timestamp).isEqualTo("1555044772083")
        assertThat(firstData.missionName).isEqualTo("Sentinel-1A")

    }


}