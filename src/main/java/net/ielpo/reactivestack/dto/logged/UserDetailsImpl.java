package net.ielpo.reactivestack.dto.logged;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import net.ielpo.reactivestack.config.AuthRole;

/**
 * @author Alberto Ielpo
 */
public class UserDetailsImpl implements UserDetails {

    private String username;
    private String password;
    private AuthRole authRole;

    public UserDetailsImpl(String username, String password, AuthRole authRole) {
        this.username = username;
        this.password = password;
        this.authRole = authRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(this.authRole.getName()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
