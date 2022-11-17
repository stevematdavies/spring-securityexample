package com.steve.tutorials.springsecurityexample.appuser;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {

     @Id
     @SequenceGenerator(
             name = "app_user_sequence",
             sequenceName = "app_user_sequence",
             allocationSize = 1
     )
     @GeneratedValue(
             strategy = GenerationType.SEQUENCE,
             generator = "app_user_sequence"
     )
     private Long id;
     private String name;
     private String username;
     private String email;
     private String password;

     @Enumerated(EnumType.STRING)
     private AppUserRole appUserRole;
     private Boolean isAccountLocked;
     private Boolean isAccountEnabled;


    public AppUser(String name,
                   String username,
                   String email,
                   String password,
                   AppUserRole appUserRole,
                   Boolean isAccountLocked,
                   Boolean isAccountEnabled) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
        this.isAccountLocked = isAccountLocked;
        this.isAccountEnabled = isAccountEnabled;
    }

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
