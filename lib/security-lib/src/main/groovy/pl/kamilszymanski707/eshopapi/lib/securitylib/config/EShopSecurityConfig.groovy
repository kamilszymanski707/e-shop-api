package pl.kamilszymanski707.eshopapi.lib.securitylib.config

import org.springframework.core.convert.converter.Converter
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter

class EShopSecurityConfig extends WebSecurityConfigurerAdapter {

    def whitelist = new HashMap()

    EShopSecurityConfig(Map<HttpMethod, String[]> whitelist) {
        this.whitelist = whitelist
    }

    @Override
    protected void configure(HttpSecurity http) {
        http
                .csrf().disable()
                .authorizeRequests { reg ->
                    whitelist.entrySet().forEach {
                        reg.antMatchers(
                                it.key as HttpMethod,
                                it.value as String[]
                        ).permitAll()
                    }

                    reg.anyRequest().authenticated()
                }
                .oauth2ResourceServer {
                    it.jwt { it.jwtAuthenticationConverter jwtAuthenticationConverter() }
                }
    }

    static Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        def jwtConverter = new JwtAuthenticationConverter()
        jwtConverter.setJwtGrantedAuthoritiesConverter new KeycloakRealmRoleConverter()
        return jwtConverter
    }
}
