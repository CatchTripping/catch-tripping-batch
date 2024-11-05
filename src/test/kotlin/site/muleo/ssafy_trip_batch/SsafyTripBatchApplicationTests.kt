package site.muleo.ssafy_trip_batch

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.muleo.ssafy_trip_batch.category_code.CategoryCodeRepository

@SpringBootTest
class SsafyTripBatchApplicationTests {


    @Test
    fun contextLoads(@Autowired categoryCodeRepository: CategoryCodeRepository) {

        val findAllByCategoryCodeStartingWithAndLength =
            categoryCodeRepository.findAllByCategoryCodeStartingWithAndLength("A01", 5)
        println()

    }

}
