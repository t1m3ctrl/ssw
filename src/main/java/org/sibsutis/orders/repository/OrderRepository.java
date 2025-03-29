package org.sibsutis.orders.repository;

import org.sibsutis.orders.model.order.Order;
import org.sibsutis.orders.model.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer_Address_City(String city);
    List<Order> findByTimeBetween(LocalDateTime start, LocalDateTime end);
    @Query("SELECT o FROM Order o JOIN o.payments p WHERE TYPE(p) = :paymentType")
    List<Order> findByPayments_PaymentType(@Param("paymentType") Class<? extends Payment> paymentType);
    List<Order> findByCustomer_Name(String name);
    List<Order> findByPayments_AmountGreaterThan(BigDecimal amount);
    List<Order> findByStatus(String status);

}
