package me.pgthinker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.pgthinker.model.domain.DocumentEntity;
import me.pgthinker.service.DocumentEntityService;
import me.pgthinker.mapper.DocumentEntityMapper;
import org.springframework.stereotype.Service;

/**
* @author pgthinker
* @description 针对表【document_entity】的数据库操作Service实现
* @createDate 2025-03-13 00:06:01
*/
@Service
public class DocumentEntityServiceImpl extends ServiceImpl<DocumentEntityMapper, DocumentEntity>
    implements DocumentEntityService{

}




