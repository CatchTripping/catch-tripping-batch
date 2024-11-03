package site.muleo.ssafy_trip_batch.area_code

import com.fasterxml.jackson.databind.ObjectMapper
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
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.DefaultUriBuilderFactory

@Configuration
class AreaCodeJobConfiguration(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
) {

    @Bean
    fun areaCodeJob(areaCodeStep: Step): Job {

        return JobBuilder("areaCodeJob", jobRepository)
            .flow(areaCodeStep)
            .end()
            .build()

    }

    @Bean
    fun areaCodeStep(
        areaCodeItemReader: ItemReader<List<AreaCodeResponse>>,
        areaCodeItemWriter: ItemWriter<List<AreaCode>>,
        areaCodeItemProcessor: ItemProcessor<List<AreaCodeResponse>, List<AreaCode>>
    ): Step {
        return StepBuilder("areaCodeStep", jobRepository)
            .chunk<List<AreaCodeResponse>, List<AreaCode>>(100, transactionManager)
            .reader(areaCodeItemReader)
            .processor(areaCodeItemProcessor)
            .writer(areaCodeItemWriter)
            .build()
    }

    @Bean
    @JobScope
    fun areaCodeItemReader(
        webClient: WebClient
    ): ItemReader<List<AreaCodeResponse>> {
        return AreaCodeItemReader(webClient)
    }

    @Bean
    fun areaCodeItemProcessor(): ItemProcessor<List<AreaCodeResponse>, List<AreaCode>> {
        return AreaCodeItemProcessor()
    }

    @Bean
    fun areaCodeItemWriter(areaCodeBulkRepository: AreaCodeBulkRepository): ItemWriter<List<AreaCode>> {
        return AreaCodeItemWriter(areaCodeBulkRepository)
    }

}