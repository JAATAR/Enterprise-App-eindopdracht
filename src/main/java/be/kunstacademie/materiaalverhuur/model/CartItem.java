package be.kunstacademie.materiaalverhuur.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representeert een item in het winkelmandje van een gebruiker.
 * Houdt verhuurperiode bij voor prijsberekening.
 */
@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    /**
     * Berekent het totaal aantal verhuurdagen.
     */
    public long getRentalDays() {
        return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    /**
     * Berekent de totale prijs voor dit cart item.
     */
    public BigDecimal getTotalPrice() {
        return product.getPricePerDay()
                .multiply(BigDecimal.valueOf(quantity))
                .multiply(BigDecimal.valueOf(getRentalDays()));
    }
}
