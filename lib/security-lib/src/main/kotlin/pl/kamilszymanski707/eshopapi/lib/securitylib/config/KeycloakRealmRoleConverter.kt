package pl.kamilszymanski707.eshopapi.lib.securitylib.config

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

internal class KeycloakRealmRoleConverter : Converter<Jwt, Collection<GrantedAuthority>> {

    private val rolePrefix = "ROLE_"
    private val resourceAccess = "resource_access"
    private val eshop = "e-shop"
    private val roles = "roles"

    override fun convert(jwt: Jwt): Collection<GrantedAuthority> {
        var result = listOf<GrantedAuthority>()

        val map = jwt.claims as Map<*, *>
        if (map.isEmpty()) return result

        val access = map[resourceAccess] as Map<*, *>
        if (access.isEmpty()) return result

        val eshop = access[eshop] as Map<*, *>
        if (eshop.isEmpty()) return result

        val roles = eshop[roles] as List<*>
        if (roles.isEmpty()) return result

        result = roles
            .map { SimpleGrantedAuthority(rolePrefix + it) }

        return result
    }
}