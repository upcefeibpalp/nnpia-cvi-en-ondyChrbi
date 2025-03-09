package cz.upce.fei.nnpiacv.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Note> notes;

    public UserResponseDto toDto() {
        return UserResponseDto.builder()
                .id(this.getId())
                .password(this.getPassword())
                .email(this.getEmail())
                .build();
    }
}
