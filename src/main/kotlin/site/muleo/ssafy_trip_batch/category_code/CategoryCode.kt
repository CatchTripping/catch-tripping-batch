package site.muleo.ssafy_trip_batch.category_code

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column


data class CategoryCode(
    @Id
    @Column("category_code")
    var categoryCode: String,
    @Column("category_name")
    var categoryName: String,
)
