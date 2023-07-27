package net.ielpo.reactivestack.config;

/**
 * @author Alberto Ielpo
 */
public class Const {
    /**
     * this constant define an useless string... it's used as fake password because
     * it's mandatory in spring security flow. Do not use for any other intent
     */
    public static String USELESS = "01b307acba4f54f55aaf";

    /**
     * Common string
     */
    public static String BAD_CREDENTIALS = "Bad credentials";
    public static String UNAUTHORIZED = "Unauthorized";
    public static String ACCESS_FORBIDDEN = "Access forbidden!";
    public static String INVALID_PARAMETERS = "Invalid parameters";
}
