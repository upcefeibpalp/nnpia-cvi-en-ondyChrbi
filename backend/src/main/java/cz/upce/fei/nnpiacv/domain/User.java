package cz.upce.fei.nnpiacv.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @NonNull
    private long id;
    @Column(unique = true)
    @NonNull
    private String email;
    @NonNull
    private String password;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Note> notes;
}
