package org.sibsutis.orders.service;

import org.sibsutis.orders.model.order.Order;
import org.sibsutis.orders.model.payment.Cash;
import org.sibsutis.orders.model.payment.Check;
import org.sibsutis.orders.model.payment.Credit;
import org.sibsutis.orders.model.payment.Payment;
import org.sibsutis.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findByAddress(String city) {
        return orderRepository.findByCustomer_Address_City(city);
    }

    public List<Order> findByTimeInterval(LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByTimeBetween(start, end);
    }

    public List<Order> findByPaymentMethod(String paymentType) {
        Class<? extends Payment> paymentClass = getPaymentClass(paymentType);
        if (paymentClass == null) {
            throw new RuntimeException("Payment type " + paymentType + " not supported");
        }
        return orderRepository.findByPayments_PaymentType(paymentClass);
    }

    public List<Order> findByCustomerName(String name) {
        return orderRepository.findByCustomer_Name(name);
    }

    public List<Order> findByPaymentStatus(BigDecimal minAmount) {
        return orderRepository.findByPayments_AmountGreaterThan(minAmount);
    }

    public List<Order> findByOrderStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    private Class<? extends Payment> getPaymentClass(String paymentType) {
        return switch (paymentType.toUpperCase()) {
            case "CREDIT" -> Credit.class;
            case "CHECK" -> Check.class;
            case "CASH" -> Cash.class;
            default -> null;
        };
    }
}
