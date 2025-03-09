package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.dto.UserRequestDto;
import cz.upce.fei.nnpiacv.dto.UserResponseDto;
import cz.upce.fei.nnpiacv.service.UserService;
import cz.upce.fei.nnpiacv.service.exception.UserAlreadyExistsException;
import cz.upce.fei.nnpiacv.service.exception.UserNotFoundException;
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

    @GetMapping("")
    public ResponseEntity<?> findUsers(@RequestParam(required = false) String email) {
        if (email == null) {
            Collection<UserResponseDto> users = userService.findUsers()
                    .stream().map(User::toDto)
                    .toList();

            return ResponseEntity.status(HttpStatus.OK).body(users);
        } else {
            User user = userService.findByEmail(email);

            if (user == null) {
                return ResponseEntity.ok(Collections.emptyList());
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonList(
                    user.toDto()
                ));
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUser(@PathVariable(name = "id") Long id) throws UserNotFoundException {
        User user = userService.findUser(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(user.toDto());
    }

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto user) throws UserAlreadyExistsException {
        log.info("Request for creating new user obtained {}", user);

        User createdUser = userService.createUser(User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdUser.toDto());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") Long id, @RequestBody UserRequestDto user) {
        log.info("Request for updating user obtained with id {}", id);

        User updatedUser = userService.updateUser(id, User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build()
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedUser.toDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long id) {
        log.info("Request for deleting user obtained with id {}", id);

        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
