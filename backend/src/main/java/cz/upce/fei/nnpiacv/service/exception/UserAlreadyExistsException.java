package cz.upce.fei.nnpiacv.service.exception;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.dto.UserRequestDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAlreadyExistsException extends Throwable {
    public UserAlreadyExistsException(User user) {
        log.error("User with email already exists, {}", user, this);
    }

    public UserAlreadyExistsException(UserRequestDto userRequestDto) {
        log.error("User with email already exists, {}", userRequestDto, this);
    }
}
