package site.muleo.ssafy_trip_batch.domain.area_based

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class AreaBasedContentBulkRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun saveAll(list: List<AreaBasedContent>) {
        val sql = """
        INSERT INTO area_based_contents
        (addr1, addr2, areacode, booktour, cat1, cat2, cat3, contentid, contenttypeid, createdtime, firstimage, firstimage2, cpyrht_div_cd, mapx, mapy, mlevel, modifiedtime, sigungucode, tel, title, zipcode) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """

        jdbcTemplate.batchUpdate(
            sql,
            list,
            list.size
        ) { ps, areaBased ->
            ps.setObject(1, areaBased.addr1)
            ps.setObject(2, areaBased.addr2)
            ps.setObject(3, areaBased.areaCode)
            ps.setObject(4, areaBased.bookTour)
            ps.setObject(5, areaBased.cat1)
            ps.setObject(6, areaBased.cat2)
            ps.setObject(7, areaBased.cat3)
            ps.setObject(8, areaBased.contentId)
            ps.setObject(9, areaBased.contentTypeId)
            ps.setObject(10, areaBased.createdTime)
            ps.setObject(11, areaBased.firstImage)
            ps.setObject(12, areaBased.firstImage2)
            ps.setObject(13, areaBased.copyrightDivCd)
            ps.setObject(14, areaBased.mapx)
            ps.setObject(15, areaBased.mapy)
            ps.setObject(16, areaBased.mlevel)
            ps.setObject(17, areaBased.modifiedTime)
            ps.setObject(18, areaBased.sigunguCode)
            ps.setObject(19, areaBased.tel)
            ps.setObject(20, areaBased.title)
            ps.setObject(21, areaBased.zipcode)
        }

    }
}