package be.kunstacademie.materiaalverhuur.service;

import be.kunstacademie.materiaalverhuur.model.CartItem;
import be.kunstacademie.materiaalverhuur.model.Product;
import be.kunstacademie.materiaalverhuur.model.User;
import be.kunstacademie.materiaalverhuur.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Service voor winkelmandje functionaliteit.
 * Beheert cart items en berekent totalen.
 */
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    @Transactional
    public CartItem addToCart(User user, Long productId, int quantity,
                              LocalDate startDate, LocalDate endDate) {
        Product product = productService.getProductById(productId);

        if (!productService.isProductAvailable(productId, quantity)) {
            throw new RuntimeException("Product niet beschikbaar in gevraagde hoeveelheid");
        }

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Startdatum moet voor einddatum liggen");
        }

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setStartDate(startDate);
        cartItem.setEndDate(endDate);

        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Transactional
    public void clearCart(User user) {
        cartItemRepository.deleteByUserId(user.getId());
    }

    /**
     * Berekent het totaalbedrag van alle items in het winkelmandje.
     */
    public BigDecimal calculateCartTotal(User user) {
        List<CartItem> items = getCartItems(user);
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getCartItemCount(User user) {
        return getCartItems(user).size();
    }
}
