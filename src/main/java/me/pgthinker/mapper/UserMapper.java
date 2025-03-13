package me.pgthinker.mapper;

import me.pgthinker.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author pgthinker
* @description 针对表【user】的数据库操作Mapper
* @createDate 2025-03-13 00:06:01
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User getUserWithRolesAndPermissions(String name);
}




