package org.sibsutis.orders.model.measurement;

import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@EqualsAndHashCode
public abstract class Measurement {
    private String name;
    private String symbol;
}
