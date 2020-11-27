package br.usjt.ucsist.cadaluno.util

import br.usjt.ucsist.cadaluno.BuildConfig

class ApiMainHeadersProvider {

    /**
     * Public headers for calls that do not need an authenticated user.
     */
    fun getPublicHeaders(): PublicHeaders =
            PublicHeaders().apply {
                putAll(getDefaultHeaders())
            }

    /**
     * Returns both the default headers and the headers that are mandatory for authenticated users.
     */
    fun getAuthenticatedHeaders(accessToken: String): AuthenticatedHeaders =
            AuthenticatedHeaders().apply {
                putAll(getDefaultHeaders())
                put(AUTHORIZATION, getBearer(accessToken))
            }

    /**
     * Default headers used on all calls.
     */
    private fun getDefaultHeaders() = mapOf(
            HEADER_ACCEPT to "application/json"
    )

//    private fun getDefaultHeaders() = mapOf(
//            ACCEPT_LANGUAGE to LANGUAGE,
//            HEADER_ACCEPT to "application/json",
//            USER_AGENT to BuildConfig.config_api_user_agent
//    )

    companion object {
        private const val ACCEPT_LANGUAGE = "Accept-Language"
        private const val USER_AGENT = "User-Agent"
        private const val AUTHORIZATION = "Authorization"
        private const val HEADER_ACCEPT = "Accept"

        private fun getBearer(accessToken: String) = "${accessToken}"
    }
}

open class ApiMainHeaders : HashMap<String, String>()
class AuthenticatedHeaders : ApiMainHeaders()
class PublicHeaders : ApiMainHeaders()