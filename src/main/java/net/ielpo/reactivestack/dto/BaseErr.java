package net.ielpo.reactivestack.dto;

/**
 * @author Alberto Ielpo
 */
public class BaseErr {

    public String status;
    public String message;

    public BaseErr(Object message) {
        this.status = "ERROR";
        if (message != null) {
            this.message = message.toString();
        }
    }

}
