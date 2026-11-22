package org.example.diplomagradle.service.user;

import org.example.diplomagradle.service.user.detail.AppUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserProvider {
    public String getCurrentUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "system";
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof AppUserDetails details) {
            return details.getUser().getName();
        }
        return "system";
    }
}

