package net.ielpo.reactivestack.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * @author Alberto Ielpo
 */
@RedisHash(timeToLive = 60)
public class TestDto {

    @Id
    private String a;
    private String b;
    private Boolean c;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public Boolean getC() {
        return c;
    }

    public void setC(Boolean c) {
        this.c = c;
    }

    public TestDto(String a, String b, Boolean c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public TestDto() {
    }

}