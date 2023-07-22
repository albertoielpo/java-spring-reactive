package net.ielpo.reactivestack.dto;

/**
 * @author Alberto Ielpo
 */
public class TokenRes {

    public TokenRes(String name, String token, Long expiryTimestamp) {
        this.name = name;
        this.token = token;
        this.expiryTimestamp = expiryTimestamp;
    }

    public String name;
    public String token;
    public Long expiryTimestamp;

}
