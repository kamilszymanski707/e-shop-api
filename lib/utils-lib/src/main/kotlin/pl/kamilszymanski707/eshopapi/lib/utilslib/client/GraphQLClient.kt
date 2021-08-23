package pl.kamilszymanski707.eshopapi.lib.utilslib.client

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.client.RestTemplate
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.LoggerConstant.Companion.log
import pl.kamilszymanski707.eshopapi.lib.utilslib.exception.ResourceNotFoundException
import java.net.URI

internal open class GraphQLClient<QL_OBJECT>(
    private val clazz: Class<QL_OBJECT>,
    private val template: RestTemplate,
) {

    protected open fun query(gql: String, url: String): QL_OBJECT? {
        val headers = HttpHeaders()
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE)

        try {
            return template.postForObject(
                URI(url),
                HttpEntity(gql, headers),
                clazz)
        } catch (e: Exception) {
            log.error(e.message)
            throw ResourceNotFoundException("Internal Server Error.")
        }
    }
}