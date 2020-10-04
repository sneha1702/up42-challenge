package com.assignment.search.application.controller

import com.assignment.search.application.service.FeaturesService
import org.apache.commons.io.IOUtils
import org.springframework.http.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.InputStream
import java.nio.charset.StandardCharsets


@RestController
@RequestMapping(value = ["/features"])
class FeaturesRestController(val featureService: FeaturesService) {
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun features() = featureService.getAll()


}
