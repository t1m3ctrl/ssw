package org.sibsutis.orders.model.order;

import jakarta.persistence.*;
import lombok.Data;
import org.sibsutis.orders.model.item.Item;
import org.sibsutis.orders.model.measurement.Quantity;

@Entity
@Table(name = "order_detail", schema = "orders")
@Data
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Embedded
    private Quantity quantity;

    private String taxStatus;
}
