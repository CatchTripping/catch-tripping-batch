package site.muleo.ssafy_trip_batch.domain.category_code

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CategoryCodeBulkRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun saveAll(list: List<CategoryCode>) {
        val sql = """
        INSERT INTO category_codes 
        (category_code, category_name) 
        VALUES (?, ?)
        """

        jdbcTemplate.batchUpdate(
            sql,
            list,
            list.size
        ) { ps, categoryCode ->
            ps.setObject(1, categoryCode.categoryCode)
            ps.setObject(2, categoryCode.categoryName)
        }

    }

    fun deleteAll() {
        val sql = "DELETE FROM category_codes"
        jdbcTemplate.update(sql)
    }
}