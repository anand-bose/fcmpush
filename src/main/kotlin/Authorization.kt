import com.google.auth.oauth2.ServiceAccountCredentials
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.ByteArrayInputStream
import java.io.File

@Serializable
data class ProjectId(
    @SerialName("project_id") val projectId: String
)

data class ServiceAccountAuthResult(
    val projectId: String,
    val accessToken: String,
)

fun getAuthToken(authType: AuthType): String {
    return when (authType) {
        is BearerTokenAuth -> authType.bearerToken
        is ServiceAccountAuth -> {
            val serviceAccountFile = File(authType.serviceAccountPath)
            check(serviceAccountFile.canRead()) {
                "Service account file ${authType.serviceAccountPath} cannot be read"
            }
            serviceAccountFile.inputStream().use { stream ->
                ServiceAccountCredentials.fromStream(stream)
                    .createScoped("https://www.googleapis.com/auth/firebase.messaging")
                    .refreshAccessToken()
                    .tokenValue
            }
        }
    }
}

fun authenticateWithServiceAccount(serviceAccountPath: String): ServiceAccountAuthResult {
    val serviceAccountFile = File(serviceAccountPath)
    check(serviceAccountFile.canRead()) {
        "Service account file $serviceAccountPath cannot be read"
    }
    val contentBytes = serviceAccountFile.readBytes()
    val contentString = contentBytes.toString(Charsets.UTF_8)
    val projectId: ProjectId = DefaultJson.decodeFromString(contentString)
    return ByteArrayInputStream(contentBytes).use { stream ->
        val scopes = listOf(
            "https://www.googleapis.com/auth/firebase.messaging"
        )
        val token = ServiceAccountCredentials.fromStream(stream)
            .createScoped(scopes)
            .refreshAccessToken()
        check(scopes.containsAll(token.scopes)) {
            "Authentication failed. Required scopes ${scopes.joinToString()} are not found"
        }
        ServiceAccountAuthResult(
            projectId = projectId.projectId,
            accessToken = token.tokenValue,
        )
    }
}