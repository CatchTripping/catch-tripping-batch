package site.muleo.ssafy_trip_batch.model


data class ApiResponse<T>(
    val response: Response<T>
)

data class Response<T>(
    val header: Header,
    val body: Body<T>
)

data class Header(
    val resultCode: String,
    val resultMsg: String
)


data class Body<T>(
    val items: Items<T>?,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class Items<T>(
    val item: List<T>
)