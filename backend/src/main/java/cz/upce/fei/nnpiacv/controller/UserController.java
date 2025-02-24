package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public Collection<User> findUsers(@RequestParam(required = false) String email) {
        if (email == null) {
            return userService.findUsers();
        } else {
            User user = userService.findByEmail(email);

            if (user == null) {
                return Collections.emptyList();
            } else {
                return Collections.singletonList(user);
            }
        }
    }

    @GetMapping("/users/{id}")
    public User findUser(@PathVariable(name = "id") Long id) {
        return userService.findUser(id);
    }
}
