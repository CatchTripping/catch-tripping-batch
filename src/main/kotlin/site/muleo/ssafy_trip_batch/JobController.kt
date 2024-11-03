package site.muleo.ssafy_trip_batch

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import site.muleo.ssafy_trip_batch.area_code.AreaCodeJobService
import site.muleo.ssafy_trip_batch.category_code.CategoryCodeJobService

@RestController
class JobController(
    private val areaCodeJobService: AreaCodeJobService,
    private val contentCodeJobService: CategoryCodeJobService,
) {

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