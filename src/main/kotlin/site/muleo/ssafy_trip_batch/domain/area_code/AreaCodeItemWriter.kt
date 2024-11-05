package site.muleo.ssafy_trip_batch.domain.area_code

import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter

class AreaCodeItemWriter(
    private val bulkRepository: AreaCodeBulkRepository
) : ItemWriter<List<AreaCode>> {

    override fun write(chunk: Chunk<out List<AreaCode>>) {
        val areaCodeList = chunk.items.flatten() // Chunk<List<AreaCode>>에서 List<AreaCode>로 변환
        bulkRepository.deleteAll()
        bulkRepository.saveAll(areaCodeList)
    }

}