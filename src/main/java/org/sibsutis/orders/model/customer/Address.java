package org.sibsutis.orders.model.customer;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
