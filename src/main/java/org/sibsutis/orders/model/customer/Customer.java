package org.sibsutis.orders.model.customer;


import jakarta.persistence.*;
import lombok.Data;
import org.sibsutis.orders.model.order.Order;

import java.util.List;

@Entity
@Table(name = "customer", schema = "orders")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Address address;


    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
