package com.stc.exporter.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    @Getter
    private int userIdx;
    private String email;
    private String password;
    private String nickname;
    private Date createdAt;
    private Date lastLogin;

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    // Implement other methods from UserDetails interface as necessary
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
        return false;
    }

    // No need for additional getters and setters due to Lombok annotations
}
