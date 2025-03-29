package org.sibsutis.orders.model.measurement;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Embeddable
@Data
public class Weight extends Measurement {
    private BigDecimal value;
}
