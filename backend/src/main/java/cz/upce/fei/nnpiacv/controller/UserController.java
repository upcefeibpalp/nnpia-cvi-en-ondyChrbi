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

    @GetMapping
    public ResponseEntity<?> findUsers(@RequestParam(required = false) String email) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        log.info("Request for deleting user with id {} obtained...", id);

        userService.deleteUser(id);

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
}
