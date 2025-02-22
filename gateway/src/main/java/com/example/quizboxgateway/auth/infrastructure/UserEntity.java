package com.example.quizboxgateway.auth.infrastructure;

import com.example.quizboxgateway.auth.auth.AuthUserDetails;
import com.example.quizboxgateway.auth.auth.SampleAuthority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
