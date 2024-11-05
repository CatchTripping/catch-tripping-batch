package site.muleo.ssafy_trip_batch.domain.area_based

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
import org.springframework.web.client.RestTemplate

@Configuration
class AreaBasedContentJobConfiguration(
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
        areaBasedItemReader: ItemReader<List<AreaBasedContentResponse>>,
        areaBasedContentItemWriter: ItemWriter<List<AreaBasedContent>>,
        areaBasedContentItemProcessor: ItemProcessor<List<AreaBasedContentResponse>, List<AreaBasedContent>>
    ): Step {
        return StepBuilder("areaBasedStep", jobRepository)
            .chunk<List<AreaBasedContentResponse>, List<AreaBasedContent>>(100, transactionManager)
            .reader(areaBasedItemReader)
            .processor(areaBasedContentItemProcessor)
            .writer(areaBasedContentItemWriter)
            .build()
    }

    @Bean
    @JobScope
    fun areaBasedItemReader(
        @Value("#{jobParameters['contentTypeId']}") contentTypeId: String,
        @Value("#{jobParameters['areaCode'] ?: ''}") areaCode: String,
        @Value("#{jobParameters['sigunguCode'] ?: ''}") sigunguCode: String,
        @Value("#{jobParameters['cat1'] ?: ''}") cat1: String,
        @Value("#{jobParameters['cat2'] ?: ''}") cat2: String,
        @Value("#{jobParameters['cat3'] ?: ''}") cat3: String,
        restTemplate: RestTemplate
    ): ItemReader<List<AreaBasedContentResponse>> {
        return AreaBasedContentItemReader(
            contentTypeId = contentTypeId,
            areaCode = areaCode,
            sigunguCode = sigunguCode,
            cat1 = cat1,
            cat2 = cat2,
            cat3 = cat3,
            restTemplate = restTemplate,
        )
    }

    @Bean
    fun areaBasedItemProcessor(): ItemProcessor<List<AreaBasedContentResponse>, List<AreaBasedContent>> {
        return AreaBasedContentItemProcessor()
    }

    @Bean
    fun areaBasedItemWriter(areaBasedContentBulkRepository: AreaBasedContentBulkRepository): ItemWriter<List<AreaBasedContent>> {
        return AreaBasedContentItemWriter(areaBasedContentBulkRepository)
    }

}