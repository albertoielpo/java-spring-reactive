package net.ielpo.reactivestack.helper;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Alberto Ielpo
 */
@Component
public class JsonHelper {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Write any object to a valid json
     * In case of String, this method escape it
     * 
     * @param value
     * @return
     */
    public String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Convert any object to another
     * 
     * @param <T>
     * @param any
     * @param clazz
     * @return
     */
    public <T> T convertAnyToObject(Object any, Class<T> clazz) {

        try {
            byte[] json = objectMapper.writeValueAsBytes(any);
            return (T) objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Convert a JSON String to an Object
     * 
     * @param <T>
     * @param json
     * @param clazz
     * @return
     */
    public <T> T convertJsonToObject(String json, Class<T> clazz) {

        try {
            return (T) objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extract a single field from JSON String
     * 
     * @param json
     * @param field
     * @return
     */
    public String extractFieldFromJson(String json, String field) {
        try {
            Map<?, ?> map = convertJsonToObject(json, Map.class);
            if (map.get(field) != null) {
                return map.get(field).toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
