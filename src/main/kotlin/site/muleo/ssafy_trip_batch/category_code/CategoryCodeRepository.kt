package site.muleo.ssafy_trip_batch.category_code

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface CategoryCodeRepository : CrudRepository<CategoryCode, Long> {

    @Query("SELECT * FROM category_codes WHERE category_code LIKE CONCAT(:categoryCode, '%') AND LENGTH(category_code) = :length")
    fun findAllByCategoryCodeStartingWithAndLength(@Param("categoryCode") categoryCode: String, @Param("length") length: Int): List<CategoryCode>

    @Query("SELECT * FROM category_codes WHERE LENGTH(category_code) = :length")
    fun findAllByCategoryCodeLength(@Param("length") length: Int): List<CategoryCode>

}