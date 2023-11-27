package spring.natanel.fightwaybackend;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spring.natanel.fightwaybackend.entity.Category;
import spring.natanel.fightwaybackend.entity.Role;
import spring.natanel.fightwaybackend.repository.CategoryRepository;
import spring.natanel.fightwaybackend.repository.RoleRepository;

@RequiredArgsConstructor
@SpringBootApplication
public class FightwayBackendApplication implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(FightwayBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findAll().isEmpty()){
            roleRepository.save(new Role(1L, "USER_ADMIN"));
            roleRepository.save(new Role(2L, "ROLE_ADMIN"));
        }

        if (categoryRepository.findAll().isEmpty()){
            categoryRepository.save(new Category(1L, "Grappling"));
            categoryRepository.save(new Category(2L, "Striking"));
            categoryRepository.save(new Category(3L, "MMA"));
        }
    }
}
