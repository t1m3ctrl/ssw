package org.sibsutis.orders.model.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CREDIT")
@Table(name = "credit", schema = "orders")
@Data
public class Credit extends Payment{
    private String number;
    private String type;
    private LocalDateTime expDate;
}
