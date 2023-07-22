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
        /** roles must be prefixed with ROLE_ due to spring security internal checks */
        return Arrays.asList(new SimpleGrantedAuthority(String.format("ROLE_%s", this.authRole.getName())));
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
        /** useless for jwt flow */
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        /** useless for jwt flow */
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        /** useless for jwt flow */
        return true;
    }

    @Override
    public boolean isEnabled() {
        /** useless for jwt flow */
        return true;
    }

}
