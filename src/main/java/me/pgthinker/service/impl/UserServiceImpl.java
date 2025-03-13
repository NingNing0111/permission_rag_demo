package me.pgthinker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import me.pgthinker.model.domain.User;
import me.pgthinker.service.UserService;
import me.pgthinker.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
* @author pgthinker
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-03-13 00:06:01
*/
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    private final PasswordEncoder passwordEncoder;

}




