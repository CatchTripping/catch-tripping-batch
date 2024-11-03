package site.muleo.ssafy_trip_batch.area_code

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class AreaCodeBulkRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun saveAll(list: List<AreaCode>) {
        val sql = """
        INSERT INTO area_codes 
        (area_code, area_name) 
        VALUES (?, ?)
        """

        jdbcTemplate.batchUpdate(
            sql,
            list,
            list.size
        ) { ps, areaCode ->
            ps.setObject(1, areaCode.areaCode)
            ps.setObject(2, areaCode.areaName)
        }

    }

    fun deleteAll() {
        val sql = "DELETE FROM area_codes"
        jdbcTemplate.update(sql)
    }
}