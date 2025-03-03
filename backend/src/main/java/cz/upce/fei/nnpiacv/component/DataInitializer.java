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
        User user = new User(0L, "admin@upce.cz", "ABC123");
        Note note = new Note(0L, "Hello world", user);

        if (!userRepository.existsById(user.getId())) {
            log.debug("Admin user created " + user);
            userRepository.save(user);
            noteRepository.save(note);
        }
    }
}
