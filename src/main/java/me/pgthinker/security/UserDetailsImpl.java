package me.pgthinker.security;

import lombok.Data;
import me.pgthinker.model.domain.Permission;
import me.pgthinker.model.domain.Role;
import me.pgthinker.model.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Project: me.pgthinker.service.security
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 13:32
 * @Description:
 */
@Data
public class UserDetailsImpl implements UserDetails {
    private final Long id;
    private final String username;
    private final String password;
    private final List<Role> roles;
    private final List<Permission> permissions;

    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles() != null ? user.getRoles() : new ArrayList<>();
        this.permissions = user.getPermissions() != null ? user.getPermissions() : new ArrayList<>();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // 角色权限：ROLE_前缀
        authorities.addAll(roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet()));

        // 具体权限
        authorities.addAll(permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toSet()));

        return authorities;
    }

}
