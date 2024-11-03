package site.muleo.ssafy_trip_batch.category_code

import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter

class CategoryCodeItemWriter(
    private val bulkRepository: CategoryCodeBulkRepository
) : ItemWriter<List<CategoryCode>> {


    override fun write(chunk: Chunk<out List<CategoryCode>>) {
        val categoryCodeList = chunk.items.flatten()
        bulkRepository.deleteAll()
        bulkRepository.saveAll(categoryCodeList)
    }


}