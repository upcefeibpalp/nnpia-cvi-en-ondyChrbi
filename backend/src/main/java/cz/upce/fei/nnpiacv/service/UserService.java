package cz.upce.fei.nnpiacv.service;

import cz.upce.fei.nnpiacv.domain.User;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final HashMap<Long, User> users = new HashMap<>();

    @PostConstruct
    public void init() {
        User user = new User(0L, "Pass123", "ondrej.chrbolka@upce.cz");
        User user1 = new User(1L, "ABC123", "pavel.krivda@upce.cz");

        users.put(user.getId(), user);
        users.put(user1.getId(), user1);
    }

    public Collection<User> findUsers() {
        return this.users.values();
    }

    public User findUser(Long id) {
        User user = users.get(id);
        log.info("Ziskan uzivatel " + user.toString());

        return user;
    }
}
