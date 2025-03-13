package me.pgthinker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.pgthinker.model.domain.UserRole;
import me.pgthinker.service.UserRoleService;
import me.pgthinker.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author pgthinker
* @description 针对表【user_role】的数据库操作Service实现
* @createDate 2025-03-13 00:06:01
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService {

}




