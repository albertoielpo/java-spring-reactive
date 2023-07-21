package net.ielpo.reactivestack.config;

/**
 * @author Alberto Ielpo
 */
public enum AuthRole {
    NONE("NONE"),
    USER("USER"),
    ADMIN("ADMIN");

    private String name;

    AuthRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
