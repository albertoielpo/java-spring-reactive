package net.ielpo.reactivestack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;

import net.ielpo.reactivestack.config.Const;
import net.ielpo.reactivestack.config.credential.InMemoryUser;
import net.ielpo.reactivestack.dao.AccessLog;
import net.ielpo.reactivestack.dto.BaseRes;
import net.ielpo.reactivestack.dto.TokenReq;
import net.ielpo.reactivestack.dto.TokenRes;
import net.ielpo.reactivestack.exception.BadRequestException;
import net.ielpo.reactivestack.exception.UnauthorizedRequestException;
import net.ielpo.reactivestack.factory.ResponseFactory;
import net.ielpo.reactivestack.manager.JwtTokenManager;
import net.ielpo.reactivestack.service.AccessLogService;
import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private InMemoryUser inMemoryUser;

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Autowired
    private AccessLogService accessLogService;

    /**
     * This method create a valid jwt token given application name and secret key
     * 
     * @param request
     * @return
     */
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BaseRes<TokenRes>> renew(@RequestBody TokenReq request) {
        if (request == null || !StringUtils.hasText(request.name) || !StringUtils.hasText(request.secret)) {
            throw new BadRequestException(Const.INVALID_PARAMETERS);
        }

        if (!inMemoryUser.isValid(request.name, request.secret)) {
            throw new UnauthorizedRequestException(Const.BAD_CREDENTIALS);
        }

        String token = jwtTokenManager.createToken(request.name);
        DecodedJWT decoded = jwtTokenManager.decode(token);

        Mono<AccessLog> dataSaved = accessLogService
                .save(new AccessLog(String.format("The user %s generate a new token", request.name)));

        Mono<BaseRes<TokenRes>> tokenResponse = ResponseFactory
                .build(new TokenRes(decoded.getSubject(), token, decoded.getExpiresAt().getTime()));

        /** merge all mono and return */
        return Mono.zip(dataSaved, tokenResponse).map(tuple -> tuple.getT2());

    }

}
