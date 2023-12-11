package com.example.ultraspringmvc;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private String userDefaultTargetUrl;
    private String adminDefaultTargetUrl;

    public void setUserDefaultTargetUrl(String userDefaultTargetUrl) {
        this.userDefaultTargetUrl = userDefaultTargetUrl;
    }

    public void setAdminDefaultTargetUrl(String adminDefaultTargetUrl) {
        this.adminDefaultTargetUrl = adminDefaultTargetUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            redirectStrategy.sendRedirect(request, response, adminDefaultTargetUrl);
        } else {
            redirectStrategy.sendRedirect(request, response, userDefaultTargetUrl);
        }
    }
}
