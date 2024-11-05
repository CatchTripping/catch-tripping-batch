package site.muleo.ssafy_trip_batch.domain.area_code


import org.springframework.batch.item.ItemProcessor


class AreaCodeItemProcessor : ItemProcessor<List<AreaCodeResponse>, List<AreaCode>> {

    override fun process(item: List<AreaCodeResponse>): List<AreaCode> {

        return item.map {

            AreaCode(
                areaCode = it.code,
                areaName = it.name,
            )

        }

    }


}