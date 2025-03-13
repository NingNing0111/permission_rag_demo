package me.pgthinker.controller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import me.pgthinker.common.BaseResponse;
import me.pgthinker.common.ResultUtils;
import me.pgthinker.model.vo.AuthVO;
import me.pgthinker.model.vo.UserLoginVO;
import me.pgthinker.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project: me.pgthinker.controller
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 21:27
 * @Description:
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @PermitAll
    public BaseResponse<AuthVO> login(@RequestBody UserLoginVO userLoginVO) {
        return ResultUtils.success(authService.login(userLoginVO));

    }
}
