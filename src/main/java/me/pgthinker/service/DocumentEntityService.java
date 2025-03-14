package me.pgthinker.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.pgthinker.model.domain.DocumentEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import me.pgthinker.model.vo.DocumentVO;
import org.springframework.web.multipart.MultipartFile;

/**
* @author pgthinker
* @description 针对表【document_entity】的数据库操作Service
* @createDate 2025-03-13 00:06:01
*/
public interface DocumentEntityService extends IService<DocumentEntity> {

    DocumentVO uploadFile(MultipartFile file, String knowledgeBaseId);

    Page<DocumentVO> managerList();
}
