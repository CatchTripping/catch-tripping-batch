package site.muleo.ssafy_trip_batch

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import site.muleo.ssafy_trip_batch.area_based.AreaBasedJobService
import kotlin.system.exitProcess

@SpringBootApplication
class SsafyTripBatchApplication

fun main(args: Array<String>) {
    if (args.none { it.startsWith("command") }) {
        runApplication<SsafyTripBatchApplication>(*args)
    } else {
        exitProcess(SpringApplication.exit(runApplication<SsafyTripBatchApplication>(*args)))
    }

}

@Component
class TripBatchJobLauncher(
    private val areaBasedJobService: AreaBasedJobService
) : CommandLineRunner {

    private val logger: Logger = LoggerFactory.getLogger(AreaBasedJobService::class.java)

    override fun run(vararg args: String?) {

        // args 에서 파라미터를 가져오는 로직
        val parameters = mutableMapOf<String, String>()


        // args 배열을 순회하여 key=value 형태로 분리
        args.forEach { arg ->
            arg?.apply {
                val parts = arg.split("=")
                if (parts.size == 2 && parts[1].isNotBlank()) {
                    parameters[parts[0]] = parts[1] // key-value 쌍을 맵에 추가
                } else {
                    logger.warn("Invalid argument format: '$arg'. Expected format: key=value")
                    return
                }
            }
        }

        if ("command" !in parameters.keys) {
            logger.error("Missing required arguments: command")
            return
        }

        if ("service" !in parameters.keys) {
            logger.error("Missing required arguments: service")
            logger.error("Please provide the required arguments in the format key=value.")
            return
        }

        when (parameters["service"]) {
            "AreaBased" -> areaBasedJobService.run(parameters)
            else -> logger.error("Invalid arguments: service=${parameters["service"]}")
        }
    }

}