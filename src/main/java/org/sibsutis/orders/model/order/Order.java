package org.sibsutis.orders.model.order;

import jakarta.persistence.*;
import lombok.Data;
import org.sibsutis.orders.model.customer.Customer;
import org.sibsutis.orders.model.payment.Payment;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order", schema = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private LocalDateTime time;

    private String status;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order")
    private List<Payment> payments;
}
