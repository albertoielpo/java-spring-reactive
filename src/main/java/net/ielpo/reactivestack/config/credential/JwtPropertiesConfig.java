package net.ielpo.reactivestack.config.credential;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtPropertiesConfig {

    @Value("${jwt.sign.secret}")
    private String signSecret;

    @Value("${jwt.sign.issuer}")
    private String signIssuer;

    @Value("${jwt.sign.validity}")
    private Long signValidity;

    public String getSignSecret() {
        return signSecret;
    }

    public String getSignIssuer() {
        return signIssuer;
    }

    public Long getSignValidity() {
        return signValidity;
    }

}
