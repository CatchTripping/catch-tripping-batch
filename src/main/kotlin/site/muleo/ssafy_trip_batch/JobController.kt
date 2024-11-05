package site.muleo.ssafy_trip_batch

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import site.muleo.ssafy_trip_batch.domain.area_based.AreaBasedContentJobService
import site.muleo.ssafy_trip_batch.domain.area_based.request.AreaBasedRequest
import site.muleo.ssafy_trip_batch.domain.area_code.AreaCodeJobService
import site.muleo.ssafy_trip_batch.domain.category_code.CategoryCodeJobService

@RestController
class JobController(
    private val areaCodeJobService: AreaCodeJobService,
    private val contentCodeJobService: CategoryCodeJobService,
    private val areaBasedContentJobService: AreaBasedContentJobService
) {

    @PostMapping("/areaBased")
    fun startAreaBased(@Valid @RequestBody request: AreaBasedRequest): ResponseEntity<String> {
        val jobExecution = areaBasedContentJobService.runByContentTypeId(request.contentTypeId)
        return jobExecution?.let {
            if (it.status.isUnsuccessful) {
                ResponseEntity.status(HttpStatus.OK)
                    .body("Job failed: ${jobExecution.allFailureExceptions.joinToString()}")
            } else {
                ResponseEntity.ok("Job completed successfully with status: ${jobExecution.status}")
            }
        } ?: ResponseEntity.status(HttpStatus.OK).body("Job instance already complete.")

    }


    @PostMapping("/areaCode")
    fun startAreaCode(): ResponseEntity<String> {
        val parameters = mutableMapOf<String, String>()
        val jobExecution = areaCodeJobService.run(parameters)
        return jobExecution?.let {
            if (it.status.isUnsuccessful) {
                ResponseEntity.status(HttpStatus.OK)
                    .body("Job failed: ${jobExecution.allFailureExceptions.joinToString()}")
            } else {
                ResponseEntity.ok("Job completed successfully with status: ${jobExecution.status}")
            }
        } ?: ResponseEntity.status(HttpStatus.OK).body("Job instance already complete.")
    }

    @PostMapping("/categoryCode")
    fun startCategoryCode(): ResponseEntity<String> {
        val parameters = mutableMapOf<String, String>()
        val jobExecution = contentCodeJobService.run(parameters)
        return jobExecution?.let {
            if (it.status.isUnsuccessful) {
                ResponseEntity.status(HttpStatus.OK)
                    .body("Job failed: ${jobExecution.allFailureExceptions.joinToString()}")
            } else {
                ResponseEntity.ok("Job completed successfully with status: ${jobExecution.status}")
            }
        } ?: ResponseEntity.status(HttpStatus.OK).body("Job instance already complete.")
    }

}