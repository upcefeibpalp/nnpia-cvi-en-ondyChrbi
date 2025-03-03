package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.dto.UserRequestDto;
import cz.upce.fei.nnpiacv.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
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

    @GetMapping("/{id}")
    public User findUser(@PathVariable(name = "id") Long id) {
        return userService.findUser(id);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto userRequestBody) {
        log.info("Request for creating userRequestBody obtained {}", userRequestBody);

        User createdUser = userService.createUser(userRequestBody.toUser());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdUser.toResponseDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        log.info("Request for deleting user with id {} obtained...", id);

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
