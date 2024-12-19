package org.example.quizbox.auth.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

public final class RequestMatchers {

    private RequestMatchers() {
    }

    public static final AntPathRequestMatcher LOGIN = new AntPathRequestMatcher("/login", HttpMethod.POST.name());

    public static final AntPathRequestMatcher REFRESH_TOKEN = new AntPathRequestMatcher("/refresh",
            HttpMethod.POST.name());

    public static final OrRequestMatcher PERMIT_ALL = new OrRequestMatcher(
            REFRESH_TOKEN,
            new AntPathRequestMatcher("/permit-all"),
            new AntPathRequestMatcher("/error"),
            new AntPathRequestMatcher("/h2-console/**")
    );

    public static final AntPathRequestMatcher DEFAULT = new AntPathRequestMatcher("/**");
}
