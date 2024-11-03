package site.muleo.ssafy_trip_batch.area_based

import org.springframework.batch.item.ItemReader
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.reactive.function.client.WebClient
import site.muleo.ssafy_trip_batch.model.ApiRequest
import site.muleo.ssafy_trip_batch.model.ApiResponse

class AreaBasedItemReader(
    private val contentTypeId: String = "",
    private val areaCode: String = "",
    private val sigunguCode: String = "",
    private val cat1: String = "",
    private val cat2: String = "",
    private val cat3: String = "",
    private val webClient: WebClient,
) : ItemReader<List<AreaBasedResponse>> {

    private val numOfRows = 100
    private var pageNo = 1
    private val mobileOS = "ETC"
    private val mobileApp = "AppTest"
    private val type = "json"
    private val listYN = "Y"
    private val arrange = "O"
    private val modifiedTime = ""

    override fun read(): List<AreaBasedResponse>? {

        val block = webClient.get()
            .uri {
                it.path("/areaBasedList1")
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

                    .build()

            }
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<ApiResponse<AreaBasedResponse>>() {})
            .doOnSuccess {
                pageNo++
            }
            .block()

        block?.apply {
            if (response.body.numOfRows > 0) {
                return response.body.items?.item
            }
        }

        return null

    }
}