package net.ielpo.reactivestack.filter;

import org.springframework.core.Ordered;

/**
 * @author Alberto Ielpo
 * @apiNote lower the number, highest is the precedence
 * @see Ordered.LOWEST_PRECEDENCE
 */
public class FilterOrder {

    /** Order -2 is required to force this filter as default */
    public final static int ERROR_EXCEPTION = -2;

    // public final static int AUTHENTICATION = 10;

}
