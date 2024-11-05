package site.muleo.ssafy_trip_batch.domain.category_code

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
    fun categoryCodeJob(cat1CodeStep: Step, cat2CodeStep: Step, cat3CodeStep: Step): Job {
        return JobBuilder("categoryCodeJob", jobRepository)
            .flow(cat1CodeStep)
            .next(cat2CodeStep)
            .next(cat3CodeStep)
            .end()
            .build()
    }

    @Bean
    fun cat1CodeStep(
        cat1CodeItemReader: ItemReader<List<CategoryCodeResponse>>,
        categoryCodeItemProcessor: ItemProcessor<List<CategoryCodeResponse>, List<CategoryCode>>,
        categoryCodeItemWriter: ItemWriter<List<CategoryCode>>,
    ): Step {
        return StepBuilder("CategoryCodeStep", jobRepository)
            .chunk<List<CategoryCodeResponse>, List<CategoryCode>>(100, transactionManager)
            .reader(cat1CodeItemReader)
            .processor(categoryCodeItemProcessor)
            .writer(categoryCodeItemWriter)
            .build()
    }

    @Bean
    fun cat2CodeStep(
        cat2CodeItemReader: ItemReader<List<CategoryCodeResponse>>,
        categoryCodeItemProcessor: ItemProcessor<List<CategoryCodeResponse>, List<CategoryCode>>,
        categoryCodeItemWriter: ItemWriter<List<CategoryCode>>,
    ): Step {
        return StepBuilder("CategoryCodeStep", jobRepository)
            .chunk<List<CategoryCodeResponse>, List<CategoryCode>>(100, transactionManager)
            .reader(cat2CodeItemReader)
            .processor(categoryCodeItemProcessor)
            .writer(categoryCodeItemWriter)
            .build()
    }

    @Bean
    fun cat3CodeStep(
        cat3CodeItemReader: ItemReader<List<CategoryCodeResponse>>,
        categoryCodeItemProcessor: ItemProcessor<List<CategoryCodeResponse>, List<CategoryCode>>,
        categoryCodeItemWriter: ItemWriter<List<CategoryCode>>,
    ): Step {
        return StepBuilder("CategoryCodeStep", jobRepository)
            .chunk<List<CategoryCodeResponse>, List<CategoryCode>>(100, transactionManager)
            .reader(cat3CodeItemReader)
            .processor(categoryCodeItemProcessor)
            .writer(categoryCodeItemWriter)
            .build()
    }


    @Bean
    fun cat1CodeItemReader(
        restTemplate: RestTemplate
    ): ItemReader<List<CategoryCodeResponse>> {
        return Cat1CodeItemReader(restTemplate)
    }

    @Bean
    fun cat2CodeItemReader(
        restTemplate: RestTemplate,
        categoryCodeRepository: CategoryCodeRepository
    ): ItemReader<List<CategoryCodeResponse>> {
        return Cat2CodeItemReader(restTemplate, categoryCodeRepository)
    }

    @Bean
    fun cat3CodeItemReader(
        restTemplate: RestTemplate
    ): ItemReader<List<CategoryCodeResponse>> {
        return Cat3CodeItemReader(restTemplate)
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