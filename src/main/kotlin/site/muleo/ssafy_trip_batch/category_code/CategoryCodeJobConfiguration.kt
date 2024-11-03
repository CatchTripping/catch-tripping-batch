package site.muleo.ssafy_trip_batch.category_code

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.web.client.RestTemplate

@Configuration
class CategoryCodeJobConfiguration(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
) {

    @Bean
    fun categoryCodeJob(categoryCodeStep: Step): Job {
        return JobBuilder("categoryCodeJob", jobRepository)
            .flow(categoryCodeStep)
            .end()
            .build()
    }

    @Bean
    fun categoryCodeStep(
        categoryCodeItemReader: ItemReader<List<CategoryCodeResponse>>,
        categoryCodeItemProcessor: ItemProcessor<List<CategoryCodeResponse>, List<CategoryCode>>,
        categoryCodeItemWriter: ItemWriter<List<CategoryCode>>,
    ): Step {
        return StepBuilder("CategoryCodeStep", jobRepository)
            .chunk<List<CategoryCodeResponse>, List<CategoryCode>>(100, transactionManager)
            .reader(categoryCodeItemReader)
            .processor(categoryCodeItemProcessor)
            .writer(categoryCodeItemWriter)
            .build()
    }

    @Bean
    fun categoryCodeItemReader(
        restTemplate: RestTemplate
    ): ItemReader<List<CategoryCodeResponse>> {
        return CategoryCodeItemReader(restTemplate)
    }


    @Bean
    fun categoryCodeItemProcessor(): ItemProcessor<List<CategoryCodeResponse>, List<CategoryCode>> {
        return CategoryCodeItemProcessor()
    }

    @Bean
    fun categoryCodeItemWriter(categoryCodeBulkRepository: CategoryCodeBulkRepository): ItemWriter<List<CategoryCode>> {
        return CategoryCodeItemWriter(categoryCodeBulkRepository)
    }


}