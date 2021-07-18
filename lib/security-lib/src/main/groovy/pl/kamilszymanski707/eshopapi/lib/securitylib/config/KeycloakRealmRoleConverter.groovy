package pl.kamilszymanski707.eshopapi.lib.securitylib.config

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    def ROLE_ = 'ROLE_'
    def resource_access = 'resource_access'
    def eshop = 'e-shop'
    def roles = 'roles'

    @Override
    Collection<GrantedAuthority> convert(Jwt jwt) {
        def result = new ArrayList()

        def map = jwt.claims as Map<String, String>
        if (map.isEmpty()) return result

        def access = map[resource_access] as Map<String, String>
        if (access.isEmpty()) return result

        def eshop = access[eshop] as Map<String, String>
        if (eshop.isEmpty()) return result

        def roles = eshop[roles] as List<String>
        if (roles.isEmpty()) return result

        result = roles.stream()
                .map { new SimpleGrantedAuthority(ROLE_ + it) }
                .toList()

        return result
    }
}
