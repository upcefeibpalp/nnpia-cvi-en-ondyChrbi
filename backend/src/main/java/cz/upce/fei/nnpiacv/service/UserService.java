package cz.upce.fei.nnpiacv.service;

import cz.upce.fei.nnpiacv.domain.User;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final Map<Long, User> users = new HashMap<>();

    @PostConstruct
    public void init() {
        Arrays.asList(
                new User(0L, "ondrej@upce.cz", "Pass123"),
                new User(1L, "tomas@upce.cz", "ABC123")
        ).forEach(u -> {
            users.put(u.getId(), u);
        });
    }

    public Collection<User> findUsers() {
        return users.values();
    }

    public User findUser(Long id) {
        User user = users.get(id);
        log.info("Ziskan uzivatel" + user.toString());

        return user;
    }
}
