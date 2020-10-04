package com.assignment.search.application.controller

import com.assignment.search.application.service.FeaturesService
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.apache.commons.io.IOUtils
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.time.LocalDate


@RestController
@RequestMapping(value = ["/features"])
class FeaturesRestController(val featureService: FeaturesService) {
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun features() = featureService.getAll()

}

@ControllerAdvice
class ControllerAdviceRequestError : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [(FeaturesNotFoundException::class)])
    fun handleUserAlreadyExists(ex: FeaturesNotFoundException, request: WebRequest): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(LocalDate.now(),
                "Feature Not Found!",
                ex.message!!
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MissingKotlinParameterException::class)
    @ResponseBody
    fun handleMissingParameterError(ex: MissingKotlinParameterException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(LocalDate.now(),
                "Parsing of Json to Java Object Failed!", ex.message!!
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }
}

data class ErrorsDetails(val time: LocalDate, val message: String, val details: String)

class FeaturesNotFoundException(override val message: String?) : Exception(message)

