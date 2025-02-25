const val ARG_PREFIX = "--"
const val ARG_AUTH_TOKEN = "${ARG_PREFIX}auth-token"
const val ARG_AUTH_SERVICE_ACCOUNT = "${ARG_PREFIX}auth-service-account"
const val ARG_TOKEN = "${ARG_PREFIX}token"
const val ARG_TOPIC = "${ARG_PREFIX}topic"
const val ARG_CONDITION = "${ARG_PREFIX}condition"
const val ARG_PROJECT_ID = "${ARG_PREFIX}project-id"
const val ARG_TITLE = "${ARG_PREFIX}title"
const val ARG_BODY = "${ARG_PREFIX}body"
const val ARG_IMAGE = "${ARG_PREFIX}image"
const val ARG_DATA = "${ARG_PREFIX}data"

sealed interface AuthType
@JvmInline
value class BearerTokenAuth(val bearerToken: String) : AuthType
@JvmInline
value class ServiceAccountAuth(val serviceAccountPath: String) : AuthType

sealed interface Target
@JvmInline
value class TokenTarget(val token: String): Target
@JvmInline
value class TopicTarget(val topic: String): Target
@JvmInline
value class ConditionTarget(val condition: String): Target

data class Arguments(
    val authType: AuthType,
    val target: Target,
    val projectId: String? = null,
    val title: String,
    val body: String,
    val image: String? = null,
    val data: Map<String, String>? = null,
)

fun parseArguments(args: Array<String>): Arguments {
    var authToken: String? = null
    var authServiceAccount: String? = null
    var token: String? = null
    var topic: String? = null
    var condition: String? = null
    var projectId: String? = null
    var title: String? = null
    var body: String? = null
    var image: String? = null
    var data: String? = null
    var i = 0
    while (i < args.size) {
        when (val key = args[i]) {
            ARG_AUTH_TOKEN -> {
                i += 1
                authToken = args[i]
            }
            ARG_AUTH_SERVICE_ACCOUNT -> {
                i += 1
                authServiceAccount = args[i]
            }
            ARG_TOKEN -> {
                i += 1
                token = args[i]
            }
            ARG_TOPIC -> {
                i += 1
                topic = args[i]
            }
            ARG_CONDITION -> {
                i += 1
                condition = args[i]
            }
            ARG_PROJECT_ID -> {
                i += 1
                projectId = args[i]
            }
            ARG_TITLE -> {
                i += 1
                title = args[i]
            }
            ARG_BODY -> {
                i += 1
                body = args[i]
            }
            ARG_IMAGE -> {
                i += 1
                image = args[i]
            }
            ARG_DATA -> {
                i += 1
                data = args[i]
            }
            else -> error("Unsupported argument: $key")
        }
        i++
    }
    return Arguments(
        authType = when {
            authToken != null -> BearerTokenAuth(authToken)
            authServiceAccount != null -> ServiceAccountAuth( authServiceAccount)
            else -> error("Please provide the bearer token with $ARG_AUTH_TOKEN flag or path to service account json with $ARG_AUTH_SERVICE_ACCOUNT flag")
        },
        target = when {
            token != null -> TokenTarget(token)
            topic != null -> TopicTarget(topic)
            condition != null -> ConditionTarget(condition)
            else -> error("Please provide the FCM token with $ARG_TOKEN flag or topic with $ARG_TOPIC flag or condition with $ARG_CONDITION flag")
        },
        projectId = projectId,
        title = title ?: error("Please provide the notification title with $ARG_TITLE flag"),
        body = body ?: error("Please provide the notification body with $ARG_BODY flag"),
        image = image,
        data = data?.let {
            DefaultJson.decodeFromString(it)
        }
    )
}