package me.pgthinker.service;

import me.pgthinker.model.vo.AuthVO;
import me.pgthinker.model.vo.UserLoginVO;

/**
 * @Project: me.pgthinker.service
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 21:30
 * @Description:
 */
public interface AuthService {

    AuthVO login(UserLoginVO userLoginVO);
}
