package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.dto.UserRequestDto;
import cz.upce.fei.nnpiacv.dto.UserResponseDto;
import cz.upce.fei.nnpiacv.service.UserService;
import cz.upce.fei.nnpiacv.service.exception.UserActiveStateException;
import cz.upce.fei.nnpiacv.service.exception.UserAlreadyExistsException;
import cz.upce.fei.nnpiacv.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> findUsers(@RequestParam(required = false, name = "email") String email) {
        if (email == null) {
            Collection<UserResponseDto> users = userService.findUsers()
                    .stream().map(User::toResponseDto)
                    .toList();

            return ResponseEntity.status(HttpStatus.OK).body(users);
        } else {
            User user = userService.findByEmail(email);

            if (user == null) {
                return ResponseEntity.ok(Collections.emptyList());
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonList(
                    user.toResponseDto()
                ));
            }
        }
    }

    @GetMapping("/{id}")
    public User findUser(@PathVariable(name = "id") Long id) throws UserNotFoundException {
        return userService.findUser(id);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto userRequestBody) throws UserAlreadyExistsException {
        log.info("Request for creating userRequestBody obtained {}", userRequestBody);

        User createdUser = userService.createUser(userRequestBody.toUser());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdUser.toResponseDto());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserRequestDto userRequestBody, @PathVariable Long id) {
        log.info("Request for updating user with id {} obtained...", id);

        User updatedUser = userService.updateUser(id, userRequestBody.toUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedUser.toResponseDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        log.info("Request for deleting user with id {} obtained...", id);

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<?> activateUser(@PathVariable Long id) throws UserNotFoundException, UserActiveStateException {
        log.info("Request for activating user with id {} obtained...", id);

        userService.activateUser(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateUser(@PathVariable Long id) throws UserNotFoundException, UserActiveStateException {
        log.info("Request for deactivating user with id {} obtained...", id);

        userService.deactivateUser(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(UserActiveStateException.class)
    public ResponseEntity<?> handleUserActiveStateException(UserActiveStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
