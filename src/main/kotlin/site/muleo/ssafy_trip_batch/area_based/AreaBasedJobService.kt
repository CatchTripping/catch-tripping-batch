package site.muleo.ssafy_trip_batch.area_based

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Service
class AreaBasedJobService(
    private val jobLauncher: JobLauncher,
    @Qualifier("areaBasedJob")
    private val job: Job,
) {

    private val logger = LoggerFactory.getLogger(AreaBasedJobService::class.java)

    fun run(parameters: Map<String, String>) {

        val requiredKeys = setOf("contentTypeId", "areaCode")

        val missingKeys = requiredKeys.filter { it !in parameters.keys }
        if (missingKeys.isNotEmpty()) {
            logger.error("Missing required arguments: ${missingKeys.joinToString(", ")}")
            logger.error("Please provide the required arguments in the format key=value.")
            return
        }


        val contentTypeValues = setOf("12", "14", "15", "25", "28", "32", "38", "39")
        val contentTypeId = parameters["contentTypeId"]
        if (contentTypeId == null || !contentTypeValues.contains(contentTypeId)) {
            logger.error("contentTypeId should be in ${contentTypeValues.joinToString(", ")}")
            return
        }

        val areaCodeValues =
            setOf("1", "2", "3", "4", "5", "6", "7", "8", "31", "32", "33", "34", "35", "36", "37", "38", "39")
        val areaCode = parameters["areaCode"]
        if (areaCode == null || !areaCodeValues.contains(areaCode)) {
            logger.error("areaCode should be in ${areaCodeValues.joinToString(", ")}")
            return
        }


        val jobParameters = JobParametersBuilder()
            .addString("contentTypeId", contentTypeId)
            .addString("areaCode", areaCode)
//            .addString("sigunguCode", parameters["sigunguCode"] ?: "")
//            .addString("cat1", parameters["cat1"] ?: "")
//            .addString("cat2", parameters["cat2"] ?: "")
//            .addString("cat3", parameters["cat3"] ?: "")
            .addString("today", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))) // 오늘 날짜 추가
            .toJobParameters()

        try {
            jobLauncher.run(job, jobParameters)
        } catch (e: JobInstanceAlreadyCompleteException) {
            logger.error("Job instance already complete. ${e.message}")
        }
    }

}