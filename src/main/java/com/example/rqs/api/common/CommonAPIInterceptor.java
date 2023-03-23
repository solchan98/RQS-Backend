package com.example.rqs.api.common;

import com.example.rqs.api.jwt.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class CommonAPIInterceptor implements HandlerInterceptor {
    private final CommonAPIAuthChecker commonAPIAuthChecker;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (!hasAnnotation(handlerMethod)) {
            return true;
        }

        MemberDetails memberDetails = commonAPIAuthChecker.checkIsAuth(request.getHeader("Authorization"));
        if (memberDetails == null) {
            return true;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetails, "", memberDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }

    private boolean hasAnnotation(HandlerMethod handlerMethod) {
        CommonAPI commonAPI = handlerMethod.getMethodAnnotation(CommonAPI.class);
        return commonAPI == null;
    }
}
