package site.muleo.ssafy_trip_batch.domain.area_based

import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter

class AreaBasedContentItemWriter(
    private val bulkRepository: AreaBasedContentBulkRepository
) : ItemWriter<List<AreaBasedContent>> {

    override fun write(chunk: Chunk<out List<AreaBasedContent>>) {
        val areaBasedList = chunk.items.flatten() // Chunk<List<AreaBased>>에서 List<AreaBased>로 변환
        bulkRepository.saveAll(areaBasedList)
    }

}