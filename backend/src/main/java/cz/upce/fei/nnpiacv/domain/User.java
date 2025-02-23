package cz.upce.fei.nnpiacv.domain;

import lombok.*;

@AllArgsConstructor
@Data
public class User {
    private Long id;
    private String password;
    private String email;
}
