package org.converter.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserCredentials extends org.springframework.security.core.userdetails.User {

    private Long id;

    public UserCredentials(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public UserCredentials(Long id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }


    public Long getId() {
        return id;
    }
}
