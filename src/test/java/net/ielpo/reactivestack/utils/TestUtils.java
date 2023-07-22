package net.ielpo.reactivestack.utils;

import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
public class TestUtils {

    /**
     * Unwrap a mono to the original type blocking the event loop
     * Never use outside test scope!
     * 
     * @param <T>
     * @param mono
     * @return
     */
    public static <T> T unwrap(Mono<T> mono) {
        return mono.block();
    }

    /**
     * 
     * @param digits
     * @return
     */
    public static Integer anyNumber(Integer digits) {
        if (digits == null || digits <= 0) {
            return 0;
        }
        String str = String.valueOf(System.currentTimeMillis());
        int len = str.length();
        return Integer.valueOf(str.substring(len - digits, len));
    }
}
