package be.kunstacademie.materiaalverhuur.service;

import be.kunstacademie.materiaalverhuur.model.*;
import be.kunstacademie.materiaalverhuur.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service voor order processing en checkout functionaliteit.
 * Converteert cart items naar orders en genereert bevestigingen.
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    /**
     * Verwerkt checkout: maakt order van cart items en leegt winkelmandje.
     * Genereert uniek bevestigingsnummer voor tracking.
     */
    @Transactional
    public Order checkout(User user) {
        List<CartItem> cartItems = cartService.getCartItems(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Winkelmandje is leeg");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.OrderStatus.CONFIRMED);
        order.setConfirmationNumber(generateConfirmationNumber());

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPricePerDay(cartItem.getProduct().getPricePerDay());
            orderItem.setStartDate(cartItem.getStartDate());
            orderItem.setEndDate(cartItem.getEndDate());
            orderItem.setSubtotal(cartItem.getTotalPrice());

            order.getOrderItems().add(orderItem);
        }

        order.setTotalAmount(cartService.calculateCartTotal(user));
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(user);

        return savedOrder;
    }

    private String generateConfirmationNumber() {
        return "CONF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order niet gevonden"));
    }
}