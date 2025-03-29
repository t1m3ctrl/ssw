package org.sibsutis.orders.model.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CHECK")
@Table(name = "check", schema = "orders")
@Data
public class Check extends Payment{
    private String name;
    private String bankID;
}
