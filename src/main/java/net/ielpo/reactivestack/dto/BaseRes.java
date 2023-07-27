package net.ielpo.reactivestack.dto;

/**
 * @author Alberto Ielpo
 */
public class BaseRes<T> {

    public String status;
    public T data;

    public BaseRes() {
        this.status = "OK";
    }

    public BaseRes(T data) {
        this.status = "OK";
        this.data = data;
    }

}
