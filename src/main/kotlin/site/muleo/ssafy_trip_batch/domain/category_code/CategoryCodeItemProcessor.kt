package site.muleo.ssafy_trip_batch.domain.category_code


import org.springframework.batch.item.ItemProcessor


class CategoryCodeItemProcessor : ItemProcessor<List<CategoryCodeResponse>, List<CategoryCode>> {

    override fun process(item: List<CategoryCodeResponse>): List<CategoryCode> {

        return item.map {

            CategoryCode(
                categoryCode = it.code,
                categoryName = it.name,
            )

        }

    }


}