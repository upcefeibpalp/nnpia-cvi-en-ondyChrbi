package cz.upce.fei.nnpiacv.service.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAuthenticationFailedException extends Throwable {
    public UserAuthenticationFailedException(String email) {
        log.info("User with email {} not found. Authentication not successful", email);
    }
}
