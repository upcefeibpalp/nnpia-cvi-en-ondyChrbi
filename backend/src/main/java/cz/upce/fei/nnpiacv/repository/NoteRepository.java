package cz.upce.fei.nnpiacv.repository;

import cz.upce.fei.nnpiacv.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
