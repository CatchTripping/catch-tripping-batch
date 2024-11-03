package site.muleo.ssafy_trip_batch.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class WebConfiguration {

//    @Bean
//    fun webClient(
//        objectMapper: ObjectMapper
//    ): WebClient {
//        val defaultUriBuilderFactory = DefaultUriBuilderFactory("https://apis.data.go.kr/B551011/KorService1")
//        defaultUriBuilderFactory.encodingMode = DefaultUriBuilderFactory.EncodingMode.NONE
//
//        return WebClient.builder()
//            .uriBuilderFactory(defaultUriBuilderFactory)
//            .codecs {
//                it.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper))
//                it.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper))
//            }
//            .build()
//
//    }

    @Bean
    fun restTemplate(objectMapper: ObjectMapper): RestTemplate {
        val messageConverters = mutableListOf<HttpMessageConverter<*>>()

        // Jackson2HttpMessageConverter 를 사용하여 JSON 변환 처리
        val jacksonConverter = MappingJackson2HttpMessageConverter(objectMapper)
        messageConverters.add(jacksonConverter)

        return RestTemplate(messageConverters)
    }
}