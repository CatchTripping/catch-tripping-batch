package site.muleo.ssafy_trip_batch.category_code

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Service
class CategoryCodeJobService(
    private val jobLauncher: JobLauncher,
    @Qualifier("categoryCodeJob")
    private val job: Job,
) {

    private val logger = LoggerFactory.getLogger(CategoryCodeJobService::class.java)

    fun run(parameters: Map<String, String>): JobExecution? {

        val jobParameters = JobParametersBuilder()
            .addString("today", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))) // 오늘 날짜 추가
            .toJobParameters()

        try {
            return jobLauncher.run(job, jobParameters)
        } catch (e: JobInstanceAlreadyCompleteException) {
            logger.error("Job instance already complete. ${e.message}")
            return null
        }
    }

}