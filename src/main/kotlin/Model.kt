import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Request(
    @SerialName("message") val message: Message
)

@Serializable
data class Message(
    @SerialName("name") val name: String? = null,
    @SerialName("token") val token: String? = null,
    @SerialName("topic") val topic: String? = null,
    @SerialName("condition") val condition: String? = null,
    @SerialName("data") val data: Map<String, String>? = null,

    @SerialName("notification") val notification: Notification,
)

@Serializable
data class Notification(
    @SerialName("title") val title: String,
    @SerialName("body") val body: String,
    @SerialName("image") val image: String? = null,
)

@Serializable
data class Response(
    @SerialName("name") val name: String? = null
)