package net.ielpo.reactivestack.config.credential;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Alberto Ielpo
 * @apiNote: In memory user db
 */
@Component
public class InMemoryUser {

    @Value("#{${allowed.user.map}}")
    private Map<String, String> allowedUserMap;

    @Value("#{${allowed.admin.map}}")
    private Map<String, String> allowedAdminMap;

    public Map<String, String> getAllowedUserMap() {
        return allowedUserMap;
    }

    public Map<String, String> getAllowedAdminMap() {
        return allowedAdminMap;
    }

    public boolean isValid(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        if (this.allowedUserMap.containsKey(username)) {
            return password.equals(this.allowedUserMap.get(username));
        }

        if (this.allowedAdminMap.containsKey(username)) {
            return password.equals(this.allowedAdminMap.get(username));
        }

        return false;
    }

}
