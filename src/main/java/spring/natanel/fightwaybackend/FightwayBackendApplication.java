package spring.natanel.fightwaybackend;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spring.natanel.fightwaybackend.dto.auth.SignUpRequestDto;
import spring.natanel.fightwaybackend.entity.Category;
import spring.natanel.fightwaybackend.entity.Product;
import spring.natanel.fightwaybackend.entity.Role;
import spring.natanel.fightwaybackend.repository.CategoryRepository;
import spring.natanel.fightwaybackend.repository.CustomerRepository;
import spring.natanel.fightwaybackend.repository.ProductRepository;
import spring.natanel.fightwaybackend.repository.RoleRepository;
import spring.natanel.fightwaybackend.service.auth.CustomerDetailServiceImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class FightwayBackendApplication implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CustomerDetailServiceImpl authService;

    public static void main(String[] args) {
        SpringApplication.run(FightwayBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findAll().isEmpty()){
            roleRepository.save(new Role(1L, "ROLE_USER"));
            roleRepository.save(new Role(2L, "ROLE_ADMIN"));
        }

        if (categoryRepository.findAll().isEmpty()){
            categoryRepository.save(new Category(1L, "Grappling"));
            categoryRepository.save(new Category(2L, "Striking"));
            categoryRepository.save(new Category(3L, "MMA"));
        }

        if (productRepository.findAll().isEmpty()) {
            // Save all 24 products
            List<Product> products = Arrays.asList(
                    new Product(52L, "Scarlet Fury Training Gloves", "Premium Impact Gloves: Elevate your striking game with our high-performance Impact Gloves.", new BigDecimal("250"), "Gloves", categoryRepository.findById(2L).orElse(null), "http://localhost:8080/assets/redGloves.png"),
                    new Product(203L, "Cerulean Sky Training Gloves", "Premium Impact Gloves: Elevate your striking game with our high-performance Impact Gloves.", new BigDecimal("250"), "Gloves", categoryRepository.findById(2L).orElse(null), "http://localhost:8080/assets/blueGloves.png"),
                    new Product(402L, "Stealth Shadow Boxing Gloves", "Premium Impact Gloves: Elevate your striking game with our high-performance Impact Gloves.", new BigDecimal("250"), "Gloves", categoryRepository.findById(2L).orElse(null), "http://localhost:8080/assets/blackGloves.png"),
                    new Product(452L, "ProTech Sparring Gloves", "Premium Impact Gloves: Elevate your striking game with our high-performance Impact Gloves.", new BigDecimal("250"), "Gloves", categoryRepository.findById(2L).orElse(null), "http://localhost:8080/assets/proTechGloves.png"),
                    new Product(652L, "Classic Logo T-Shirt", "Comfortable and stylish T-shirt featuring our classic logo.", new BigDecimal("29.99"), "Shirts", categoryRepository.findById(3L).orElse(null), "http://localhost:8080/assets/fightwayShirt.png"),
                    new Product(653L, "Graphic Print Training Shirt", "Breathable training shirt with a unique graphic print design.", new BigDecimal("34.99"), "Shirts", categoryRepository.findById(3L).orElse(null), "http://localhost:8080/assets/whiteShirt.png"),
                    new Product(654L, "Performance Long Sleeve Tee", "High-performance long sleeve tee for intense workout sessions.", new BigDecimal("39.99"), "Shirts", categoryRepository.findById(3L).orElse(null), "http://localhost:8080/assets/blackLongShirt.png"),
                    new Product(655L, "Premium Jiu-Jitsu Gi", "High-quality Jiu-Jitsu Gi for serious practitioners.", new BigDecimal("129.99"), "BJJ - GI", categoryRepository.findById(1L).orElse(null), "http://localhost:8080/assets/premiumGi.png"),
                    new Product(656L, "Competition-Ready BJJ Kimono", "Specially designed BJJ Kimono for competition-ready performance.", new BigDecimal("149.99"), "BJJ - GI", categoryRepository.findById(1L).orElse(null), "http://localhost:8080/assets/kimono.png"),
                    new Product(657L, "Classic Brazilian Jiu-Jitsu Gi", "Timeless design with excellent craftsmanship for Brazilian Jiu-Jitsu enthusiasts.", new BigDecimal("119.99"), "BJJ - GI", categoryRepository.findById(1L).orElse(null), "http://localhost:8080/assets/classicGi.png"),
                    new Product(702L, "Heavy Duty Punching Bag", "Durable and heavy-duty punching bag for intense workouts.", new BigDecimal("349.99"), "Punching Bags", categoryRepository.findById(2L).orElse(null), "http://localhost:8080/assets/punchingBag.png"),
                    new Product(752L, "Pro Series Headgear", "Top-quality headgear designed for maximum protection during striking training.", new BigDecimal("79.99"), "Protective Equipment", categoryRepository.findById(2L).orElse(null), "http://localhost:8080/assets/headgear.png"),
                    new Product(753L, "GelTech Boxing Gloves", "Premium boxing gloves with GelTech padding for superior hand protection.", new BigDecimal("99.99"), "Gloves", categoryRepository.findById(2L).orElse(null), "http://localhost:8080/assets/gelTechGloves.png"),
                    new Product(802L, "Grappling Shin Guards", "High-density shin guards designed for intensive grappling sessions.", new BigDecimal("59.99"), "Protective Equipment", categoryRepository.findById(1L).orElse(null), "http://localhost:8080/assets/shinGuards.png"),
                    new Product(803L, "Submission Wrestling Headgear", "Specialized headgear for protection during submission wrestling training.", new BigDecimal("69.99"), "Protective Equipment", categoryRepository.findById(2L).orElse(null), "http://localhost:8080/assets/blueHeadgear.png"),
                    new Product(852L, "Pro Trainer's Focus Mitts", "High-quality focus mitts designed for trainers to enhance striking precision.", new BigDecimal("89.99"), "Coach Equipment", categoryRepository.findById(2L).orElse(null), "http://localhost:8080/assets/mitts.png"),
                    new Product(902L, "Professional PU Focus Mitts", "A high-quality pair of focus mitts made with durable PU material. Ideal for precision training in various combat sports.", new BigDecimal("79.99"), "Coach Equipment", categoryRepository.findById(2L).orElse(null), "http://localhost:8080/assets/bigMitts.png"),
                    new Product(952L, "MMA Black Shirt", "Comfortable and stylish black shirt featuring our classic logo.", new BigDecimal("29.99"), "Shirts", categoryRepository.findById(3L).orElse(null), "http://localhost:8080/assets/mmaBlackShirt.png"),
                    new Product(1002L, "Protective Shinguards", "Ensure your safety during training with these durable and comfortable shinguards.", new BigDecimal("49.99"), "Protective Equipment", categoryRepository.findById(2L).orElse(null), "http://localhost:8080/assets/regularShinguards.png"),
                    new Product(1052L, "MMA Red Logo Shirt", "Show your passion for MMA with this stylish red shirt featuring our iconic logo.", new BigDecimal("34.99"), "Shirts", categoryRepository.findById(3L).orElse(null), "http://localhost:8080/assets/mmaRedShirt.png"),
                    new Product(1102L, "Kontact Elbow Protector", "Protect your elbows during intense training sessions with these high-quality Kontact elbow protectors.", new BigDecimal("64.99"), "Protective Equipment", categoryRepository.findById(1L).orElse(null), "http://localhost:8080/assets/kontactElbowProtector.png")
            );

            // Save all products
            productRepository.saveAll(products);
        }

        if (customerRepository.findAll().isEmpty()){
            authService.signAdminUp(new SignUpRequestDto("Nate","natanell777@gmail.com","Nate777"));
        }
    }
}
