package be.kunstacademie.materiaalverhuur.service;

import be.kunstacademie.materiaalverhuur.model.Product;
import be.kunstacademie.materiaalverhuur.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service voor product-gerelateerde business logica.
 * Biedt methoden voor catalogus weergave en filtering.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAvailableProducts() {
        return productRepository.findByAvailableTrue();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product niet gevonden met id: " + id));
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }

    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Controleert of product beschikbaar is voor verhuur op basis van voorraad.
     */
    public boolean isProductAvailable(Long productId, int requestedQuantity) {
        Product product = getProductById(productId);
        return product.getAvailable() && product.getStockQuantity() >= requestedQuantity;
    }
}
