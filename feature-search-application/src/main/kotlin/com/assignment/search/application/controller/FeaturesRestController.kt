package com.assignment.search.application.controller

import com.assignment.search.application.domain.ResultFeaturesData
import com.assignment.search.application.service.FeaturesService
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.apache.commons.io.IOUtils
import org.springframework.http.*
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.time.LocalDate


@RestController
@RequestMapping(value = ["/features"])
class FeaturesRestController(val featureService: FeaturesService) {
    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun features() = featureService.getAll()

    @GetMapping("{featureId}", produces = [APPLICATION_JSON_VALUE])
    fun features(@PathVariable("featureId") featureId: String): ResultFeaturesData? {
        return featureService.getOne(featureId) ?: throw FeaturesNotFoundException("FeatureId: $featureId")
    }

    @GetMapping(value = ["{featureId}/quicklook"])
    fun featuresQuicklook(@PathVariable("featureId") featureId: String): Any {
        val quicklookData = featureService.getQuicklook(featureId)
        if (quicklookData == null) {
            throw FeaturesNotFoundException("FeatureId: $featureId")
        } else {
            val targetStream: InputStream = IOUtils.toInputStream(quicklookData, StandardCharsets.UTF_8)
            val pngBytes = IOUtils.toByteArray(targetStream)
            val headers = HttpHeaders().also {
                it.contentType = MediaType.IMAGE_PNG
                it.contentLength = quicklookData.length.toLong()
                it.cacheControl = CacheControl.noCache().headerValue
            }

            return ResponseEntity(pngBytes, headers, HttpStatus.OK)
        }
    }
}

@ControllerAdvice
class ControllerAdviceRequestError : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [(FeaturesNotFoundException::class)])
    fun handlFeaturesNotFound(ex: FeaturesNotFoundException, request: WebRequest): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(LocalDate.now(),
                "Feature Not Found!",
                ex.message!!
        )
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MissingKotlinParameterException::class)
    @ResponseBody
    fun handleMissingParameterError(ex: MissingKotlinParameterException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(LocalDate.now(),
                "Parsing of Json to Java Object Failed!", ex.message!!
        )
        return ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

data class ErrorsDetails(val time: LocalDate, val message: String, val details: String)

class FeaturesNotFoundException(override val message: String?) : Exception(message)

