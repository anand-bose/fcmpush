import io.ktor.client.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

fun buildHttpClient() = HttpClient(Java) {
    install(ContentNegotiation) {
        json(DefaultJson)
    }
}