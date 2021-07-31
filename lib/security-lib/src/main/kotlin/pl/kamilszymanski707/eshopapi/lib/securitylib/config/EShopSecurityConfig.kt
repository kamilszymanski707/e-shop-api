package pl.kamilszymanski707.eshopapi.lib.securitylib.config

import org.springframework.core.convert.converter.Converter
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.POST
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter

open class EShopSecurityConfig(
    private val whitelist: Map<HttpMethod, Array<String>> =
        hashMapOf(Pair(POST, arrayOf("/graphql"))),
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests { reg ->
                whitelist.entries.forEach {
                    reg.antMatchers(
                        it.key,
                        *it.value
                    ).permitAll()
                }

                reg.anyRequest().authenticated()
            }
            .oauth2ResourceServer { configurer ->
                configurer.jwt { it.jwtAuthenticationConverter(jwtAuthenticationConverter()) }
            }
    }

    private fun jwtAuthenticationConverter(): Converter<Jwt, AbstractAuthenticationToken> {
        val jwtConverter = JwtAuthenticationConverter()
        jwtConverter.setJwtGrantedAuthoritiesConverter(KeycloakRealmRoleConverter())
        return jwtConverter
    }
}
