package cz.upce.fei.nnpiacv.service.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundException extends Throwable {
    public UserNotFoundException(Long id) {
        log.error("User with id {} not found", id, this);
    }
}
