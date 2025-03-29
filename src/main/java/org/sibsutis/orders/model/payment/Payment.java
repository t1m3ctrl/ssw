package org.sibsutis.orders.model.payment;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.sibsutis.orders.model.order.Order;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "payment_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "payment", schema = "orders")
@EqualsAndHashCode
public abstract class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private BigDecimal amount;
}
