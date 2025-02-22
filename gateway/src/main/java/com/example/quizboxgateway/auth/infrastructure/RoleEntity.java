package com.example.quizboxgateway.auth.infrastructure;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {
    private long userId;

    private String role;
}
