package cz.upce.fei.nnpiacv.component;

import cz.upce.fei.nnpiacv.domain.Role;
import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.repository.RoleRepository;
import cz.upce.fei.nnpiacv.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Role role = new Role(0L, "ADMIN");
        role = roleRepository.save(role);

        userRepository.save(new User(0L, "admin@upce.cz", "ABC123", role));

        log.info("Admin user created.");
    }
}
