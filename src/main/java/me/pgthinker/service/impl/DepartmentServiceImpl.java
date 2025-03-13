package me.pgthinker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.pgthinker.model.domain.Department;
import me.pgthinker.service.DepartmentService;
import me.pgthinker.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;

/**
* @author pgthinker
* @description 针对表【department】的数据库操作Service实现
* @createDate 2025-03-13 00:06:01
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService{

}




