package pl.kamilszymanski707.eshopapi.services.catalog.config

import org.springframework.http.HttpMethod.POST
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import pl.kamilszymanski707.eshopapi.lib.securitylib.config.EShopSecurityConfig

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
internal class SecurityConfig : EShopSecurityConfig(mapOf(Pair(POST, arrayOf("/graphql"))))
