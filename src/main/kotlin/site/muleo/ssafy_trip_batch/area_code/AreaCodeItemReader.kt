package site.muleo.ssafy_trip_batch.area_code

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

    private var pageNo = 1
    private val mobileOS = "ETC"
    private val mobileApp = "AppTest"
    private val type = "json"

    override fun read(): List<AreaCodeResponse>? {

        val url = UriComponentsBuilder.fromHttpUrl("https://apis.data.go.kr/B551011/KorService1/areaCode1")
            .queryParam("serviceKey", ApiRequest.serviceKey)
            .queryParam("pageNo", pageNo)
            .queryParam("MobileOS", mobileOS)
            .queryParam("MobileApp", mobileApp)
            .queryParam("_type", type)
            .build(true).toUri()

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