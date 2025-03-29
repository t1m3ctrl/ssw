package org.sibsutis.orders.model.item;

import jakarta.persistence.*;
import lombok.Data;
import org.sibsutis.orders.model.measurement.Weight;

@Entity
@Table(name = "item", schema = "orders")
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Weight shippingWeight;

    private String description;
}