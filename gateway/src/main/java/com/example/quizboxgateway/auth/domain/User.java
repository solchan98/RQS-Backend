package com.example.quizboxgateway.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Setter
    private Long id;

    private String email;

    private String name;

    private String encryptedPassword;

    private List<String> roles;

    private OauthType oauthType;

    private String oauthId;

    public static User newOauthMember(OauthType oauthType, String oauthId) {
        // TODO: EMAIL 값객체 생성
        String email = oauthId.concat("@").concat(oauthType.name()).concat(".com");
        return new User(null, email, "", "", List.of(Role.GUEST.name()), oauthType, oauthId);
    }

    public AuthUserDetails toAuthUserDetails() {
        List<SampleAuthority> authorities = roles.stream()
                .map(SampleAuthority::new)
                .toList();
        return new AuthUserDetails(id, email, name, encryptedPassword, authorities, oauthType, oauthId, signUpStatus());
    }

    /**
     * TODO: Test case
     *
     * @return
     */
    public boolean signUpStatus() {
        return (Objects.isNull(name) || name.isBlank());
    }
}
