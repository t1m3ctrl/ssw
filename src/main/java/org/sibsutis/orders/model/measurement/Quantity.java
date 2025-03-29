package org.sibsutis.orders.model.measurement;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Embeddable
@Data
public class Quantity extends Measurement {
    private Integer value;
}
