package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.dto.UserLoginRequestDto;
import cz.upce.fei.nnpiacv.dto.UserLoginResponseDto;
import cz.upce.fei.nnpiacv.service.AuthenticationService;
import cz.upce.fei.nnpiacv.service.exception.UserAuthenticationFailedException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) throws UserAuthenticationFailedException {
        String token = authenticationService.login(userLoginRequestDto);

        return ResponseEntity.ok(UserLoginResponseDto.builder()
                .token(token)
                .build());
    }

    @ExceptionHandler(UserAuthenticationFailedException.class)
    public ResponseEntity<String> handleUserAuthenticationFailedException(UserAuthenticationFailedException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }
}
