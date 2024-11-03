package site.muleo.ssafy_trip_batch.area_based

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AreaBasedJobConfiguration(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
) {

    @Bean
    fun areaBasedJob(areaBasedStep: Step): Job {

        return JobBuilder("areaBasedJob", jobRepository)
            .flow(areaBasedStep)
            .end()
            .build()

    }

    @Bean
    fun areaBasedStep(
        areaBasedItemReader: ItemReader<List<AreaBasedResponse>>,
        areaBasedItemWriter: ItemWriter<List<AreaBased>>,
        areaBasedItemProcessor: ItemProcessor<List<AreaBasedResponse>, List<AreaBased>>
    ): Step {
        return StepBuilder("areaBasedStep", jobRepository)
            .chunk<List<AreaBasedResponse>, List<AreaBased>>(100, transactionManager)
            .reader(areaBasedItemReader)
            .processor(areaBasedItemProcessor)
            .writer(areaBasedItemWriter)
            .build()
    }

    @Bean
    @JobScope
    fun areaBasedItemReader(
        @Value("#{jobParameters['contentTypeId']}") contentTypeId: String,
        @Value("#{jobParameters['areaCode']}") areaCode: String,
        @Value("#{jobParameters['sigunguCode'] ?: ''}") sigunguCode: String,
        @Value("#{jobParameters['cat1'] ?: ''}") cat1: String,
        @Value("#{jobParameters['cat2'] ?: ''}") cat2: String,
        @Value("#{jobParameters['cat3'] ?: ''}") cat3: String,
        webClient: WebClient
    ): ItemReader<List<AreaBasedResponse>> {
        return AreaBasedItemReader(
            contentTypeId = contentTypeId,
            areaCode = areaCode,
            sigunguCode = sigunguCode,
            cat1 = cat1,
            cat2 = cat2,
            cat3 = cat3,
            webClient = webClient,
        )
//        return AreaBasedItemReader(
//            contentTypeId = "12",
//            areaCode = "1",
//            sigunguCode = "",
//            cat1 = "",
//            cat2 = "",
//            cat3 = "",
//            webClient = webClient,
//        )
    }

    @Bean
    fun areaBasedItemProcessor(): ItemProcessor<List<AreaBasedResponse>, List<AreaBased>> {
        return AreaBasedItemProcessor()
    }

    @Bean
    fun areaBasedItemWriter(areaBasedBulkRepository: AreaBasedBulkRepository): ItemWriter<List<AreaBased>> {
        return AreaBasedItemWriter(areaBasedBulkRepository)
    }

}