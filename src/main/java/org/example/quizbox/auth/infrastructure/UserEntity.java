package org.example.quizbox.auth.infrastructure;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.quizbox.auth.auth.AuthUserDetails;
import org.example.quizbox.auth.auth.SampleAuthority;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    private Long id;

    private String email;

    private String name;

    private String encryptedPassword;

    private List<String> roles;

    public AuthUserDetails toAuthUserDetails() {
        List<SampleAuthority> authorities = roles.stream()
                .map(SampleAuthority::new)
                .toList();
        return new AuthUserDetails(id, email, name, encryptedPassword, authorities);
    }
}
