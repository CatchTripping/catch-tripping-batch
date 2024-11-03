package site.muleo.ssafy_trip_batch.area_based

import java.time.LocalDateTime

data class AreaBasedContent(
    val addr1: String? = null,
    val addr2: String? = null,
    val areaCode: Byte? = null,
    val bookTour: Boolean? = null,
    val cat1: String? = null,
    val cat2: String? = null,
    val cat3: String? = null,
    val contentId: Int,
    val contentTypeId: Byte,
    val createdTime: LocalDateTime,
    val firstImage: String? = null,
    val firstImage2: String? = null,
    val copyrightDivCd: String? = null,
    val mapx: Double? = null,
    val mapy: Double? = null,
    val mlevel: Byte? = null,
    val modifiedTime: LocalDateTime,
    val sigunguCode: Byte? = null,
    val tel: String? = null,
    val title: String,  // NOT NULL 이므로 필수
    val zipcode: String? = null,
)
