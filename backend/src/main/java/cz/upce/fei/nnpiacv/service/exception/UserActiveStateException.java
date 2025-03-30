package cz.upce.fei.nnpiacv.service.exception;

import cz.upce.fei.nnpiacv.domain.User;

public class UserActiveStateException extends Exception {
    public UserActiveStateException(User user, boolean active) {
        super("User: " + user + " is already " + (active ? "active" : "inactive"));
    }
}
