package net.ielpo.reactivestack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import net.ielpo.reactivestack.config.AuthRole;
import net.ielpo.reactivestack.config.Const;
import net.ielpo.reactivestack.config.credential.InMemoryUser;
import net.ielpo.reactivestack.dto.logged.UserDetailsImpl;
import net.ielpo.reactivestack.exception.ForbiddenRequestException;
import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    @Autowired
    private InMemoryUser inMemoryUser;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        String useless = String.format("{noop}%s", Const.USELESS);

        if (inMemoryUser.getAllowedUserMap().containsKey(username.toLowerCase())) {
            /** allowed user */
            return Mono.just(new UserDetailsImpl(username, useless, AuthRole.USER));
        }

        if (inMemoryUser.getAllowedAdminMap().containsKey(username.toLowerCase())) {
            /** allowed admin */
            return Mono.just(new UserDetailsImpl(username, useless, AuthRole.ADMIN));
        }

        throw new ForbiddenRequestException(Const.ACCESS_FORBIDDEN);
    }

}
