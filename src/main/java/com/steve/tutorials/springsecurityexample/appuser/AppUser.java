package com.steve.tutorials.springsecurityexample.appuser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class AppUser implements UserDetails {

     private Long id;
     private String name;
     private String username;
     private String email;
     private String password;
     private AppUserRole appUserRole;
     private Boolean isAccountLocked;
     private Boolean isAccountEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority(appUserRole.name())
        );
    }

    @Override
    public String getPassword(){
         return password;
    }

    @Override
    public String getUsername(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return !isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return isAccountEnabled;
    }
}
