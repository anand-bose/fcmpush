import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.TimeZone

suspend fun main(args: Array<String>) {
    val arguments = parseArguments(args)
    val token: String
    val projectId: String
    when (arguments.authType) {
        is BearerTokenAuth -> {
            token = arguments.authType.bearerToken
            projectId = arguments.projectId ?: error("Please provide the project ID with $ARG_PROJECT_ID flag")
        }
        is ServiceAccountAuth -> {
            val result = authenticateWithServiceAccount(arguments.authType.serviceAccountPath)
            token = result.accessToken
            projectId = result.projectId
            println("Project ID: $projectId")
            if (arguments.printBearerToken) {
                println("Bearer token: $token")
                val expiryTimeFormatted = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(result.expiry),
                    ZoneId.systemDefault(),
                ).format(
                    DateTimeFormatter.ofLocalizedDateTime(
                        FormatStyle.FULL,
                        FormatStyle.FULL,
                    ).withZone(ZoneId.systemDefault())
                )
                println("Expires on: $expiryTimeFormatted")
            }
        }
    }
    val url = URLBuilder("https://fcm.googleapis.com/v1/projects/")
        .appendPathSegments(projectId, "messages:send")
        .build()
    val http = buildHttpClient()
    val response: Response = http.post(url) {
        header("Authorization", "Bearer $token")
        contentType(ContentType.Application.Json)
        setBody(Request(
            message = Message(
                token = if (arguments.target is TokenTarget) arguments.target.token else null,
                topic = if (arguments.target is TopicTarget) arguments.target.topic else null,
                condition = if (arguments.target is ConditionTarget) arguments.target.condition else null,
                data = arguments.data,
                notification = Notification(
                    title = arguments.title,
                    body = arguments.body,
                    image = arguments.image,
                )
            )
        ))
    }.body()
    println("Message sent: ${response.name}")
}