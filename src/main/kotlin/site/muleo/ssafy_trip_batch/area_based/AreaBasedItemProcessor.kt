package site.muleo.ssafy_trip_batch.area_based


import org.springframework.batch.item.ItemProcessor

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class AreaBasedItemProcessor : ItemProcessor<List<AreaBasedResponse>, List<AreaBased>> {

    private val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")

    override fun process(item: List<AreaBasedResponse>): List<AreaBased> {

        return item.map {

            val bookTour: Boolean? = it.let { areaBasedResponse ->
                if (areaBasedResponse.booktour == null) {
                    null
                } else {
                    areaBasedResponse.booktour == "1"
                }
            }

            AreaBased(
                addr1 = it.addr1,
                addr2 = it.addr2,
                areaCode = it.areacode,
                bookTour = bookTour,
                cat1 = it.cat1,
                cat2 = it.cat2,
                cat3 = it.cat3,
                contentId = it.contentid,
                contentTypeId = it.contenttypeid,
                createdTime = LocalDateTime.parse(it.createdtime, formatter),
                firstImage = it.firstimage,
                firstImage2 = it.firstimage2,
                copyrightDivCd = it.cpyrhtDivCd,
                mapx = it.mapx,
                mapy = it.mapy,
                mlevel = it.mlevel,
                modifiedTime = LocalDateTime.parse(it.modifiedtime, formatter),
                sigunguCode = it.sigungucode,
                tel = it.tel,
                title = it.title,
                zipcode = it.zipcode,
            )

        }

    }


}