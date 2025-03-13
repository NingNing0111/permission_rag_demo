package me.pgthinker.service.impl;

import lombok.RequiredArgsConstructor;
import me.pgthinker.common.ErrorCode;
import me.pgthinker.exception.BusinessException;
import me.pgthinker.mapper.DepartmentMapper;
import me.pgthinker.mapper.UserMapper;
import me.pgthinker.mapper.UserRoleMapper;
import me.pgthinker.model.domain.Department;
import me.pgthinker.model.domain.Role;
import me.pgthinker.model.domain.User;
import me.pgthinker.model.vo.AuthVO;
import me.pgthinker.model.vo.UserLoginVO;
import me.pgthinker.security.UserDetailsImpl;
import me.pgthinker.service.AuthService;
import me.pgthinker.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Project: me.pgthinker.service.impl
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 21:31
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final DepartmentMapper departmentMapper;

    @Override
    public AuthVO login(UserLoginVO userLoginVO) {
        String username = userLoginVO.getUsername();
        String password = userLoginVO.getPassword();
        User user = userMapper.getUserWithRolesAndPermissions(username);
        if(user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(ErrorCode.USER_ACCOUNT_ERROR);
        }
        AuthVO authVO = new AuthVO();
        Optional.of(user.getRoles()).ifPresent(item -> {
             authVO.setRoles(item.stream().map(Role::getName).toList());
        });

        Optional.of(user.getDepartmentId()).ifPresent(item-> {
            Department department = departmentMapper.selectById(item);
            if(department != null) {
                authVO.setDepartment(department.getName());
            }
        });
        String token = jwtUtil.generateToken(new UserDetailsImpl(user));
        authVO.setToken(token);
        authVO.setUsername(username);
        return authVO;
    }
}
