package me.pgthinker.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.pgthinker.mapper.UserMapper;
import me.pgthinker.model.domain.DocumentEntity;
import me.pgthinker.model.domain.User;
import me.pgthinker.model.vo.DocumentVO;
import me.pgthinker.security.UserDetailsImpl;
import me.pgthinker.service.DocumentEntityService;
import me.pgthinker.mapper.DocumentEntityMapper;
import me.pgthinker.utils.DocumentUtil;
import me.pgthinker.utils.MinioUtil;
import me.pgthinker.utils.SecurityFrameworkUtil;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author pgthinker
* @description 针对表【document_entity】的数据库操作Service实现
* @createDate 2025-03-13 00:06:01
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentEntityServiceImpl extends ServiceImpl<DocumentEntityMapper, DocumentEntity>
    implements DocumentEntityService{

    private final VectorStore vectorStore;
    private final MinioUtil minioUtil;
    private final UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public DocumentVO uploadFile(MultipartFile file, String knowledgeBaseId) {
        Resource resource = file.getResource();
        UserDetails loginUser = SecurityFrameworkUtil.getLoginUser();
        String username = loginUser.getUsername();
        User user = userMapper.getUserWithRolesAndPermissions(username);
        Long loginUserId = user.getId();
        String path = minioUtil.uploadFile(file);
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setUploader(loginUserId);
        documentEntity.setPath(path);
        documentEntity.setFileName(file.getOriginalFilename());
        documentEntity.setBaseId(knowledgeBaseId);
        this.save(documentEntity);
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
        List<Document> rawDocumentList = tikaDocumentReader.read();
        List<Document> handledDocumentList = DocumentUtil.simpleHandler(rawDocumentList);
        // 添加用户id、部门id信息、文档id、知识库id
        List<Document> hasMetaDocumentList = handledDocumentList.stream().map(item -> {
            Map<String, Object> metadata = item.getMetadata();
            metadata.put("user_id", loginUserId);
            metadata.put("department_id", user.getDepartmentId());
            metadata.put("knowledge_base_id", knowledgeBaseId);
            metadata.put("document_id", documentEntity.getId());
            return new Document(item.getText(), metadata);
        }).collect(Collectors.toList());
        vectorStore.accept(hasMetaDocumentList);


        return transfer(documentEntity);
    }

    @Override
    public Page<DocumentVO> managerList() {
        return null;
    }


    private DocumentVO transfer(DocumentEntity documentEntity) {
        DocumentVO documentVO = new DocumentVO();
        BeanUtils.copyProperties(documentEntity, documentVO);
        return documentVO;
    }
}




