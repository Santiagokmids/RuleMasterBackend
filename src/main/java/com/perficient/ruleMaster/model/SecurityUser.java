package com.perficient.ruleMaster.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record SecurityUser(RuleMasterUser ruleMasterUser) implements UserDetails{

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(ruleMasterUser)
                .map(RuleMasterUser::getRole).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return ruleMasterUser.getPassword();
    }

    @Override
    public String getUsername() {
        return ruleMasterUser.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
