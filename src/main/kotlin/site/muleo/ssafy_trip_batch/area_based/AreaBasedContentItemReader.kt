package site.muleo.ssafy_trip_batch.area_based

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

class AreaBasedContentItemReader(
    private val contentTypeId: String = "",
    private val areaCode: String = "",
    private val sigunguCode: String = "",
    private val cat1: String = "",
    private val cat2: String = "",
    private val cat3: String = "",
    private val restTemplate: RestTemplate,
) : ItemReader<List<AreaBasedContentResponse>> {

    private val numOfRows = 100
    private var pageNo = 1
    private val mobileOS = "ETC"
    private val mobileApp = "AppTest"
    private val type = "json"
    private val listYN = "Y"
    private val arrange = "O"
    private val modifiedTime = ""

    override fun read(): List<AreaBasedContentResponse>? {
        val url = UriComponentsBuilder.fromHttpUrl("https://apis.data.go.kr/B551011/KorService1/areaBasedList1")
            .queryParam("serviceKey", ApiRequest.serviceKey)
            .queryParam("numOfRows", numOfRows)
            .queryParam("pageNo", pageNo)
            .queryParam("MobileOS", mobileOS)
            .queryParam("MobileApp", mobileApp)
            .queryParam("_type", type)
            .queryParam("listYN", listYN)
            .queryParam("arrange", arrange)
            .queryParam("contentTypeId", contentTypeId)
            .queryParam("areaCode", areaCode)
            .queryParam("sigunguCode", sigunguCode)
            .queryParam("cat1", cat1)
            .queryParam("cat2", cat2)
            .queryParam("cat3", cat3)
            .queryParam("modifiedtime", modifiedTime)
            .toUriString()

        // Using RestTemplate to send the GET request
        val responseEntity: ResponseEntity<ApiResponse<AreaBasedContentResponse>> =
            restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<ApiResponse<AreaBasedContentResponse>>() {})

        if (responseEntity.statusCode != HttpStatus.OK) {
            throw ResponseStatusException(
                responseEntity.statusCode,
                "Failed to fetch area codes: ${responseEntity.statusCode} with $url"
            )
        }

        val responseBody = responseEntity.body
        responseBody?.apply {
            if (response.body.numOfRows > 0) {
                pageNo++
                return response.body.items?.item
            }
            return null
        }

        return null
    }

}