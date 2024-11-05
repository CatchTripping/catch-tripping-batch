package site.muleo.ssafy_trip_batch.domain.area_code

import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemReader
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder
import site.muleo.ssafy_trip_batch.model.ApiRequest
import site.muleo.ssafy_trip_batch.model.ApiResponse

class AreaCodeItemReader(
    private val restTemplate: RestTemplate
) : ItemReader<List<AreaCodeResponse>> {

    private val logger = LoggerFactory.getLogger(AreaCodeItemReader::class.java)

    private var pageNo = 1
    private val mobileOS = "ETC"
    private val mobileApp = "AppTest"
    private val type = "json"

    override fun read(): List<AreaCodeResponse>? {

        // cat 1
        // https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=Vd9WAkYUqieoe7823m9baFDddi3miXBM9Q0p05%2Fa8sPogz5vJgAXyj%2B4kMlaGx1nupb%2BxWNpbs0ItTU9%2FbfQFA%3D%3D&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json
        // https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=Vd9WAkYUqieoe7823m9baFDddi3miXBM9Q0p05%2Fa8sPogz5vJgAXyj%2B4kMlaGx1nupb%2BxWNpbs0ItTU9%2FbfQFA%3D%3D&pageNo=2&MobileOS=ETC&MobileApp=AppTest&_type=json
        // https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=Vd9WAkYUqieoe7823m9baFDddi3miXBM9Q0p05%2Fa8sPogz5vJgAXyj%2B4kMlaGx1nupb%2BxWNpbs0ItTU9%2FbfQFA%3D%3D&pageNo=3&MobileOS=ETC&MobileApp=AppTest&_type=json

        // cat 2
        //


        val url = UriComponentsBuilder.fromHttpUrl("https://apis.data.go.kr/B551011/KorService1/areaCode1")
            .queryParam("serviceKey", ApiRequest.serviceKey)
            .queryParam("pageNo", pageNo)
            .queryParam("MobileOS", mobileOS)
            .queryParam("MobileApp", mobileApp)
            .queryParam("_type", type)
            .build(true).toUri()

        logger.info("API REQUEST URL areaCode1 : {}", url.toString())

        val responseEntity: ResponseEntity<ApiResponse<AreaCodeResponse>> =
            restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<ApiResponse<AreaCodeResponse>>() {})

        if (responseEntity.statusCode != HttpStatus.OK) {
            throw ResponseStatusException(
                responseEntity.statusCode,
                "Failed to fetch area codes: ${responseEntity.statusCode}"
            )
        }

        val responseBody = responseEntity.body
        responseBody?.apply {
            if (response.body.numOfRows > 0) {
                pageNo++
                return response.body.items?.item
            }
        }

        return null

    }
}