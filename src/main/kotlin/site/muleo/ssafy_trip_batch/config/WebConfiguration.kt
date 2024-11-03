package site.muleo.ssafy_trip_batch.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.DefaultUriBuilderFactory

@Component
class WebConfiguration {

    @Bean
    fun webClient(
        objectMapper: ObjectMapper
    ): WebClient {
        val defaultUriBuilderFactory = DefaultUriBuilderFactory("https://apis.data.go.kr/B551011/KorService1")
        defaultUriBuilderFactory.encodingMode = DefaultUriBuilderFactory.EncodingMode.NONE

        return WebClient.builder()
            .uriBuilderFactory(defaultUriBuilderFactory)
            .codecs {
                it.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper))
                it.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper))
            }
            .build()

    }
}