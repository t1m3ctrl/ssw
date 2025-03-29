package org.sibsutis.orders.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sibsutis.orders.model.customer.Address;
import org.sibsutis.orders.model.customer.Customer;
import org.sibsutis.orders.model.order.Order;
import org.sibsutis.orders.repository.CustomerRepository;
import org.sibsutis.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("orders")
class OrderServiceIntegrationTest {
    @Container
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
            new PostgreSQLContainer<>("postgres:15");

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerRepository customerRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        POSTGRES_CONTAINER.start();
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    }

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        prepareTestData();
    }

    private void prepareTestData() {
        Customer customer = new Customer();
        Address address = new Address(
                "New York",
                "5th Ave",
                "10001");
        customer.setName("John Doe");
        customer.setAddress(address);
        customer = customerRepository.save(customer);

        Order order = new Order();
        order.setCustomer(customer);
        order.setTime(LocalDateTime.now());
        order.setStatus("PENDING");
        orderRepository.save(order);
    }

    @Test
    void testFindByAddress() {
        List<Order> orders = orderService.findByAddress("New York");
        assertThat(orders).isNotEmpty();
    }

    @Test
    void testFindByTimeInterval() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        List<Order> orders = orderService.findByTimeInterval(start, end);
        assertThat(orders).isNotEmpty();
    }

    @Test
    void testFindByPaymentMethod() {
        List<Order> orders = orderService.findByPaymentMethod("CREDIT");
        assertThat(orders).isEmpty();
    }

    @Test
    void testFindByPaymentMethodInvalid() {
        String invalidPaymentMethod = "INVALID_PAYMENT_TYPE";

        Exception exception = Assertions.assertThrows(
                RuntimeException.class, () -> orderService.findByPaymentMethod(invalidPaymentMethod)
        );

        assertThat(exception.getMessage()).contains(invalidPaymentMethod + " not supported");
    }

    @Test
    void testFindByCustomerName() {
        List<Order> orders = orderService.findByCustomerName("John Doe");
        assertThat(orders).isNotEmpty();
    }

    @Test
    void testFindByPaymentStatus() {
        List<Order> orders = orderService.findByPaymentStatus(BigDecimal.ZERO);
        assertThat(orders).isEmpty();
    }

    @Test
    void testFindByOrderStatus() {
        List<Order> orders = orderService.findByOrderStatus("PENDING");
        assertThat(orders).isNotEmpty();
    }
}