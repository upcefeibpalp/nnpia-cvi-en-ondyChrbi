package cz.upce.fei.nnpiacv.domain;

import cz.upce.fei.nnpiacv.dto.UserResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    @Column(columnDefinition = "boolean default true")
    private Boolean active = true;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Note> notes;

    public UserResponseDto toResponseDto() {
        return UserResponseDto.builder()
                .id(getId())
                .email(getEmail())
                .password(getPassword())
                .active(getActive())
                .build();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                '}';
    }
}
