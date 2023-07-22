package net.ielpo.reactivestack.utils;

import org.springframework.util.Assert;

import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 * @apiNote Use only in TEST scope!
 */
public class TestUtils {

    /**
     * Unwrap a mono to the original type blocking the event loop
     * 
     * @param <T>
     * @param mono
     * @return
     */
    public static <T> T unwrap(Mono<T> mono) {
        return mono.block();
    }

    /**
     * Generate any number given digits as parameters
     * The number is not random at all, it's just a substring of the "n" digit of
     * the current timestamp
     * 
     * @param digits
     * @return
     */
    public static Integer anyNumber(Integer digits) {
        Assert.isTrue(digits != null && digits > 0, "Digits must be > 0");
        String str = String.valueOf(System.currentTimeMillis());
        int len = str.length();
        return Integer.valueOf(str.substring(len - digits, len));
    }
}
