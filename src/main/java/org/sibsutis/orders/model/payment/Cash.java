package org.sibsutis.orders.model.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CASH")
@Table(name = "cash", schema = "orders")
@Data
public class Cash extends Payment{
    private float cashTendered;
}
