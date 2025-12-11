package be.kunstacademie.materiaalverhuur.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entiteit die een verhuurbaar product representeert.
 * Bevat alle noodzakelijke informatie voor catalogus en voorraad.
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product naam is verplicht")
    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @NotNull(message = "Prijs is verplicht")
    @Positive(message = "Prijs moet positief zijn")
    @Column(nullable = false)
    private BigDecimal pricePerDay;

    @NotNull
    @Column(nullable = false)
    private Integer stockQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean available = true;
}
