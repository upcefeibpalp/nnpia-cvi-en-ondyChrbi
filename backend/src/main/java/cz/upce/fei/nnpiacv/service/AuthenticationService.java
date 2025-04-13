package cz.upce.fei.nnpiacv.service;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.dto.UserLoginRequestDto;
import cz.upce.fei.nnpiacv.service.exception.UserAuthenticationFailedException;
import cz.upce.fei.nnpiacv.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    public String login(UserLoginRequestDto userLoginRequestDto) throws UserAuthenticationFailedException {
        return login(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword());
    }

    public String login(String email, String password) throws UserAuthenticationFailedException {
        User user;
        try {
            user = userService.findUserByEmailAndPassword(email, password);
        } catch (UserNotFoundException e) {
            throw new UserAuthenticationFailedException(email);
        }

        return jwtTokenService.generateToken(user);
    }
}
