package com.iset.customer.service.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Custom JWT Converter to map Keycloak roles (from 'realm_access') into Spring Security authorities.
 */
@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {


    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),

                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());


        return new JwtAuthenticationToken(
                jwt,
                authorities,
                jwt.getClaimAsString("preferred_username")
        );
    }

    /**
     * Extracts roles from the Keycloak 'realm_access' claim.
     */
    private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
        // Check if the 'realm_access' claim exists
        if (!jwt.getClaims().containsKey("realm_access")) {
            return Collections.emptySet();
        }

        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");

        if (!realmAccess.containsKey("roles")) {
            return Collections.emptySet();
        }


        Collection<String> roles = (Collection<String>) realmAccess.get("roles");

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toSet());
    }
}