package site.muleo.ssafy_trip_batch.area_based

import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter

class AreaBasedItemWriter(
    private val bulkRepository: AreaBasedBulkRepository
) : ItemWriter<List<AreaBased>> {

    override fun write(chunk: Chunk<out List<AreaBased>>) {
        val areaBasedList = chunk.items.flatten() // Chunk<List<AreaBased>>에서 List<AreaBased>로 변환
        bulkRepository.saveAll(areaBasedList)
    }

}