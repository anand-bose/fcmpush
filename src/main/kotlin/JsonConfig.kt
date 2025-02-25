import kotlinx.serialization.json.Json

val DefaultJson = Json {
    isLenient = true
    explicitNulls = false
    ignoreUnknownKeys = true
    encodeDefaults = true
}