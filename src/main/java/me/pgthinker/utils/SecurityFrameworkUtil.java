package me.pgthinker.utils;

import lombok.extern.slf4j.Slf4j;
import me.pgthinker.model.domain.User;
import me.pgthinker.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Project: me.pgthinker.utils
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 22:54
 * @Description:
 */
@Slf4j
public class SecurityFrameworkUtil {
    public static final String AUTHORIZATION_BEARER = "Bearer";

    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        return context.getAuthentication();
    }

    public static UserDetailsImpl getLoginUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getPrincipal() instanceof UserDetailsImpl ? (UserDetailsImpl) authentication.getPrincipal() : null;
    }

    public static Long getCurrUserId() {
        UserDetailsImpl loginUser = getLoginUser();
        return loginUser != null ? loginUser.getId() : null;
    }

}
