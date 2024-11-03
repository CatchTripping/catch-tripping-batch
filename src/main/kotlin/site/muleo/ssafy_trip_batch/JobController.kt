package site.muleo.ssafy_trip_batch

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import site.muleo.ssafy_trip_batch.area_code.AreaCodeJobService

@RestController
class JobController(
    private val areaCodeJobService: AreaCodeJobService
) {

    @PostMapping("/api/jobs/areaCode")
    fun startAreaCode(): Mono<ResponseEntity<String>> {
        return Mono.fromCallable {
            val parameters = mutableMapOf<String, String>()
            areaCodeJobService.run(parameters)
            ResponseEntity.ok("Job started successfully")
        }.onErrorReturn(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build())
    }
}