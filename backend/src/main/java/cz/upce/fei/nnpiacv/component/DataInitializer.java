package cz.upce.fei.nnpiacv.component;

import cz.upce.fei.nnpiacv.domain.Note;
import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.repository.NoteRepository;
import cz.upce.fei.nnpiacv.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    @Override
    public void run(String... args) {
        User user = new User("admin@upce.cz", "ABC123");
        Note note = new Note("Hello world", user);

        if (!userRepository.existsByEmail(user.getEmail())) {
            log.debug("Admin user created " + user);
            userRepository.save(user);
            noteRepository.save(note);
        }
    }
}
