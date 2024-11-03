package site.muleo.ssafy_trip_batch.area_code

import org.springframework.batch.item.ItemReader
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.reactive.function.client.WebClient
import site.muleo.ssafy_trip_batch.model.ApiRequest
import site.muleo.ssafy_trip_batch.model.ApiResponse

class AreaCodeItemReader(
    private val webClient: WebClient,
) : ItemReader<List<AreaCodeResponse>> {

    private val mobileOS = "ETC"
    private val mobileApp = "AppTest"
    private val type = "json"

    override fun read(): List<AreaCodeResponse>? {

        val block = webClient.get()
            .uri {
                it.path("/areaCode1")
                    .queryParam("serviceKey", ApiRequest.serviceKey)
                    .queryParam("MobileOS", mobileOS)
                    .queryParam("MobileApp", mobileApp)
                    .queryParam("_type", type)

                    .build()

            }
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<ApiResponse<AreaCodeResponse>>() {})
            .block()

        block?.apply {
            if (response.body.numOfRows > 0) {
                return response.body.items?.item
            }
        }

        return null

    }
}