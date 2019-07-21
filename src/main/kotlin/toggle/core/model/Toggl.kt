package toggle.core.model

import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.coroutines.awaitObjectResponseResult
import com.github.kittinunf.fuel.gson.gsonDeserializerOf
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.runBlocking

private const val workspaceId = "94268"
private const val apiKey = "9494a7475481dfe7aff97b6b782c2b7c"
private const val defaultPwd = "api_token"
private const val user = "info@lunatech.com"
private const val reportingApiUrl = "https://toggl.com/reports/api/v2/details?user_agent=$user&workspace_id=$workspaceId"
private const val apiUrl = "https://toggl.com/api/v8/"
private const val workspaceClients = "workspaces/%s/clients"


fun fetchDetailsReport() {
    runBlocking {
        reportingApiUrl.httpGet()
            .authentication()
            .basic(apiKey, defaultPwd)
            .awaitObjectResponseResult(gsonDeserializerOf(DetailsReport::class.java)).third
            .fold(
                { data -> println(data) },
                { error -> println("An error of type ${error.exception} happened: ${error.message}") }
            )
    }
}