package cz.upce.fei.nnpiacv.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"app_user\"")
public class User {
    @Id
    private long id;
    private String email;
    private String password;
    @ManyToOne
    private Role role;

    public User(long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
