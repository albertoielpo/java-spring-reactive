package net.ielpo.reactivestack.dto;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

/**
 * @author Alberto Ielpo
 */
public class BikeDto {

    @Id
    private String uuid;
    private String name;
    private BigDecimal price;

    public BikeDto() {

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}