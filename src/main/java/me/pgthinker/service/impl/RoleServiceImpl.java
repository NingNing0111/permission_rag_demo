package me.pgthinker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.pgthinker.model.domain.Role;
import me.pgthinker.service.RoleService;
import me.pgthinker.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author pgthinker
* @description 针对表【role】的数据库操作Service实现
* @createDate 2025-03-13 00:06:01
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




