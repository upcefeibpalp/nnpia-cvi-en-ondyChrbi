package cz.upce.fei.nnpiacv.service;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findUsers() {
        return this.userRepository.findAll();
    }

    public User findUser(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);

        if (user.isPresent()) {
            log.info("Ziskan uzivatel" + user);
            return user.get();
        } else {
            log.error("Uzivatel nebyl nalezen" + email);
            return null;
        }
    }

    public User findUser(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        log.info("Ziskan uzivatel" + user);

        return user.get();
    }
}
