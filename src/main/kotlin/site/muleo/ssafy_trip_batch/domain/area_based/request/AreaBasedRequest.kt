package site.muleo.ssafy_trip_batch.domain.area_based.request

import jakarta.validation.constraints.NotNull

data class AreaBasedRequest(
    @NotNull
    val contentTypeId: Long
)
