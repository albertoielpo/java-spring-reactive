package net.ielpo.reactivestack.manager;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import net.ielpo.reactivestack.config.credential.JwtPropertiesConfig;

/**
 * @author Alberto Ielpo
 */
@Controller
public class JwtTokenManager {

    @Autowired
    private JwtPropertiesConfig jwtPropertiesConfig;

    /**
     * Create a token
     * 
     * @param username
     * @return
     */
    public String createToken(String username) {
        try {
            Date validity = new Date(new Date().getTime() + jwtPropertiesConfig.getSignValidity());
            Algorithm algorithm = Algorithm.HMAC256(jwtPropertiesConfig.getSignSecret());
            String token = JWT.create()
                    .withIssuer(jwtPropertiesConfig.getSignIssuer())
                    .withExpiresAt(validity)
                    .withIssuedAt(new Date())
                    .withSubject(username)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            return null;
        }
    }

    /**
     * Decode the token
     * 
     * @param token
     * @return
     */
    public DecodedJWT decode(String token) {
        try {
            return JWT.decode(token);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extract the username
     * 
     * @param token
     * @return
     */
    public String extractUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Check the token validity (verify + expiry date)
     * 
     * @param token
     * @return
     */
    public boolean isValidToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtPropertiesConfig.getSignSecret());
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(jwtPropertiesConfig.getSignIssuer()).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getExpiresAt().after(new Date());
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * Given a token and userDetails check the validity
     * 
     * @param token
     * @param userDetails
     * @return
     */
    public boolean isValid(String token, UserDetails userDetails) {
        return isValid(token, userDetails.getUsername());
    }

    /**
     * Given a token and an username check the validity
     * 
     * @param token
     * @param username
     * @return
     */
    public boolean isValid(String token, String username) {
        if (isValidToken(token)) {
            return username.equals(extractUsername(token));
        }
        return false;
    }

}
