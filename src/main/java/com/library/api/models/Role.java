package com.library.api.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.library.api.models.Permission.*;

@RequiredArgsConstructor
public enum Role {

    ADMIN(Set.of(ADMIN_READ, ADMIN_CREATE, USER_READ, USER_CREATE)), USER(Set.of(USER_READ, USER_CREATE));

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream().map(authority -> new SimpleGrantedAuthority(authority.getPermission())).collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
