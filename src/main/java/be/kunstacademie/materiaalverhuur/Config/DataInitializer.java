package be.kunstacademie.materiaalverhuur.Config;

import be.kunstacademie.materiaalverhuur.model.Category;
import be.kunstacademie.materiaalverhuur.model.Product;
import be.kunstacademie.materiaalverhuur.model.User;
import be.kunstacademie.materiaalverhuur.repository.CategoryRepository;
import be.kunstacademie.materiaalverhuur.repository.ProductRepository;
import be.kunstacademie.materiaalverhuur.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;

/**
 * Initialiseert database met test data bij eerste opstart.
 * Maakt categorieën, producten en een test gebruiker aan.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Override
    public void run(String... args) {
        logger.info("=== DataInitializer gestart ===");

        try {
            if (categoryRepository.count() == 0) {
                logger.info("Geen categorieën gevonden, initialiseren...");
                initializeCategories();
                initializeProducts();
                logger.info("Categorieën en producten succesvol aangemaakt!");
            } else {
                logger.info("Database bevat al {} categorieën", categoryRepository.count());
                logger.info("Database bevat al {} producten", productRepository.count());
            }

            if (!userService.existsByUsername("student")) {
                logger.info("Test gebruiker wordt aangemaakt...");
                initializeTestUser();
                logger.info("Test gebruiker succesvol aangemaakt!");
            } else {
                logger.info("Test gebruiker bestaat al");
            }
        } catch (Exception e) {
            logger.error("FOUT bij initialiseren van data: ", e);
        }

        logger.info("=== DataInitializer voltooid ===");
    }

    private void initializeCategories() {
        Category belichting = new Category();
        belichting.setName("Belichting");
        belichting.setDescription("Lampen en verlichtingsmateriaal");
        categoryRepository.save(belichting);

        Category kabels = new Category();
        kabels.setName("Kabels");
        kabels.setDescription("Diverse kabels en connectoren");
        categoryRepository.save(kabels);

        Category controlepanelen = new Category();
        controlepanelen.setName("Controlepanelen");
        controlepanelen.setDescription("DMX controllers en lichtmixers");
        categoryRepository.save(controlepanelen);

        Category podium = new Category();
        podium.setName("Podiumelementen");
        podium.setDescription("Podiumelementen en constructies");
        categoryRepository.save(podium);
    }

    private void initializeProducts() {
        Category belichting = categoryRepository.findByName("Belichting").orElseThrow();
        Category kabels = categoryRepository.findByName("Kabels").orElseThrow();
        Category controlepanelen = categoryRepository.findByName("Controlepanelen").orElseThrow();
        Category podium = categoryRepository.findByName("Podiumelementen").orElseThrow();

        // Belichting producten
        createProduct("LED PAR 64", "Professionele LED PAR lamp met RGB kleuren",
                new BigDecimal("15.00"), 8, belichting);
        createProduct("Moving Head Spot", "Intelligente moving head spotlight",
                new BigDecimal("35.00"), 4, belichting);
        createProduct("Fresnel Spotlight 1000W", "Klassieke theater spotlight",
                new BigDecimal("12.00"), 6, belichting);

        // Kabels
        createProduct("XLR Kabel 10m", "Professionele XLR kabel voor audio/DMX",
                new BigDecimal("2.50"), 20, kabels);
        createProduct("Powercon Kabel 5m", "Powercon stroomkabel",
                new BigDecimal("3.00"), 15, kabels);
        createProduct("DMX Kabel 15m", "3-pins DMX kabel voor lichtbesturing",
                new BigDecimal("3.50"), 12, kabels);

        // Controlepanelen
        createProduct("DMX Controller 512", "512 kanalen DMX controller",
                new BigDecimal("45.00"), 3, controlepanelen);
        createProduct("Lichtmixer 24 Kanalen", "Analoge lichtmixer",
                new BigDecimal("25.00"), 2, controlepanelen);

        // Podiumelementen
        createProduct("Podiumdek 2x1m", "Modulair podiumdek element",
                new BigDecimal("8.00"), 10, podium);
        createProduct("Truss 2m", "Aluminium truss element",
                new BigDecimal("10.00"), 8, podium);
    }

    private void createProduct(String name, String description, BigDecimal price,
                               int stock, Category category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPricePerDay(price);
        product.setStockQuantity(stock);
        product.setCategory(category);
        product.setAvailable(true);
        productRepository.save(product);
    }

    private void initializeTestUser() {
        User user = new User();
        user.setUsername("student");
        user.setEmail("student@kunstacademie.be");
        user.setPassword("password123");
        user.setFirstName("Test");
        user.setLastName("Student");
        user.setStudentNumber("S12345");
        user.setRoles(new HashSet<>());
        user.getRoles().add("STUDENT");
        userService.registerUser(user);
    }
}
