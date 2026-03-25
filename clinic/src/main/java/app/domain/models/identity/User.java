package app.domain.models.identity;

import app.domain.models.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class User extends Person {

    private String username;

    private String password;

    private Role role;
}
