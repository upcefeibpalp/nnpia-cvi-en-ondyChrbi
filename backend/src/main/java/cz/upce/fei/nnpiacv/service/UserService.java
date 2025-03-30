package cz.upce.fei.nnpiacv.service;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.repository.UserRepository;
import cz.upce.fei.nnpiacv.service.exception.UserActiveStateException;
import cz.upce.fei.nnpiacv.service.exception.UserAlreadyExistsException;
import cz.upce.fei.nnpiacv.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Collection<User> findUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findUser(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        log.debug("Ziskan uzivatel " + user.orElse(null));

        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        return user.orElse(null);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public User createUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(user);
        }

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User updateUser(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) {
            return null;
        }

        User updatedUser = existingUser.get();
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());

        return userRepository.save(updatedUser);
    }

    @Transactional
    public void activateUser(Long id) throws UserNotFoundException, UserActiveStateException {
        changeUserActiveState(id, true);
    }

    @Transactional
    public void deactivateUser(Long id) throws UserNotFoundException, UserActiveStateException {
        changeUserActiveState(id, false);
    }

    @Transactional
    protected void changeUserActiveState(Long id, boolean activate) throws UserNotFoundException, UserActiveStateException {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getActive() == activate) {
                throw new UserActiveStateException(user, activate);
            }

            user.setActive(activate);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException(id);
        }
    }
}