package net.ielpo.reactivestack.controller;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import net.ielpo.reactivestack.config.Const;
import net.ielpo.reactivestack.dao.AccessLog;
import net.ielpo.reactivestack.dto.TokenReq;
import net.ielpo.reactivestack.dto.TokenRes;
import net.ielpo.reactivestack.exception.UnauthorizedRequestException;
import net.ielpo.reactivestack.manager.JwtTokenManager;
import net.ielpo.reactivestack.service.AccessLogService;
import net.ielpo.reactivestack.util.TestUtils;
import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TokenControllerTest {

    private Logger logger = LoggerFactory.getLogger(TokenControllerTest.class);

    @Mock
    private AccessLogService accessLogService;

    @InjectMocks
    @Autowired
    private TokenController tokenController;

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Test
    void testRenewInvalidSecret() {
        Assert.notNull(tokenController, "token controller must be defined");

        TokenReq tokenReq = new TokenReq() {
            {
                this.name = "user_test"; // correct
                this.secret = "invalid_secret";
            }
        };

        try {
            TestUtils.unwrap(tokenController.renew(tokenReq));
            this.logger.error("Renew succedeed with an invalid secret!");
            Assert.isTrue(false, "Renew succedeed with an invalid secret!");
        } catch (Exception e) {
            Assert.isTrue(e instanceof UnauthorizedRequestException,
                    "Invalid credential should throw an UnauthorizedRequestException");
            Assert.isTrue(e.getMessage().equals(Const.BAD_CREDENTIALS), "Message should be bad credentials");
        }

    }

    @Test
    void testRenew() {

        Mockito.when(accessLogService.save(Mockito.any())).thenReturn(Mono.just(new AccessLog("test")));
        Assert.notNull(tokenController, "token controller must be defined");

        TokenReq tokenReq = new TokenReq() {
            {
                this.name = "user_test"; // correct
                this.secret = "user_test"; // correct
            }
        };

        var res = TestUtils.unwrap(tokenController.renew(tokenReq));
        TokenRes tokenRes = res.data;
        Assert.notNull(tokenRes, "Res data must be valid");

        Assert.isTrue(tokenRes.expiryTimestamp > new Date().getTime(), "Expiry timestamp should be > than now");
        Assert.isTrue(tokenReq.name.equals(tokenRes.name), "TokenReq.name and TokenRes.name must be equals");

        Assert.isTrue(jwtTokenManager.isValid(tokenRes.token, tokenReq.name), "Token must be valid");
        Assert.isTrue(tokenRes.name.equals(jwtTokenManager.extractUsername(tokenRes.token)),
                "TokenReq.name and sub must be equals");

        Assert.isTrue(tokenRes.expiryTimestamp.equals(jwtTokenManager.decode(tokenRes.token).getExpiresAt().getTime()),
                "TokenRes.timestamp must be equals to exp");

    }

}
