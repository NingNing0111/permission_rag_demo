package me.pgthinker.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import me.pgthinker.common.ErrorCode;
import me.pgthinker.exception.BusinessException;
import me.pgthinker.mapper.DepartmentMapper;
import me.pgthinker.mapper.DocumentEntityMapper;
import me.pgthinker.mapper.UserMapper;
import me.pgthinker.model.domain.DocumentEntity;
import me.pgthinker.model.domain.User;
import me.pgthinker.security.UserDetailsImpl;
import me.pgthinker.service.VectorSearchService;
import me.pgthinker.utils.SecurityFrameworkUtil;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Project: me.pgthinker.service.impl
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/14 09:45
 * @Description:
 */
@RequiredArgsConstructor
@Service
public class VectorSearchServiceImpl implements VectorSearchService {

    private final VectorStore vectorStore;
    private final UserMapper userMapper;
    private final DepartmentMapper departmentMapper;
    private final DocumentEntityMapper documentEntityMapper;



    @Override
    public List<String> getTopSimilarityText(int topK, String searchText) {
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .topK(topK)
                        .query(searchText)
                        .build()
        );
        if(documents != null && !documents.isEmpty()) {
            return documents.stream().map(Document::getText).toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getCurUserAccessTopText(int topK, String searchText) {
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .topK(topK)
                        .query(searchText)
                        .filterExpression(buildBaseAccessFilter())
                        .build()
        );
        if(documents != null && !documents.isEmpty()) {
            return documents.stream().map(Document::getText).toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getUserAccessTopText(int topK, String searchText, String knowledgeBaseId) {
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .topK(topK)
                        .query(searchText)
                        .filterExpression(buildBaseAccessFilterByKnowledgeBase(knowledgeBaseId))
                        .build()
        );
        if(documents != null && !documents.isEmpty()) {
            return documents.stream().map(Document::getText).toList();
        }
        return Collections.emptyList();
    }

    // {"source": "张德宁-开题报告.docx", "user_id"、 "document_id"、"department_id"、"knowledge_base_id"}
    // 整个部门下上传的知识库 对应的部门成员 都能看到
    // 1. 查询当前用户所在部门的所有成员 user_ids
    // 2. user_id in user_ids
    // 3. 查询当前用户所在部门的所有成员 上传的文档id document_ids
    // 4. document_id in document_ids
    private String buildBaseAccessFilter(){
        // 当前用户所在部门
        UserDetailsImpl loginUser = SecurityFrameworkUtil.getLoginUser();
        List<Long> userIds = new ArrayList<>();
        ArrayList<Long> documentIds = new ArrayList<>();
        Optional.ofNullable(loginUser).ifPresent(user->{
            String username = user.getUsername();
            User userWithRolesAndPermissions = userMapper.getUserWithRolesAndPermissions(username);
            if(ObjectUtils.isEmpty(userWithRolesAndPermissions)){
                throw new BusinessException(ErrorCode.USER_NOT_FOUNT);
            }
            Long departmentId = userWithRolesAndPermissions.getDepartmentId();
            LambdaQueryWrapper<User> userQW = new LambdaQueryWrapper<>();
            userQW.eq(User::getDepartmentId, departmentId);
            List<User> users = userMapper.selectList(userQW);
            Optional.ofNullable(users).ifPresent(item -> {
                userIds.addAll(item.stream().map(User::getId).toList());
            });
            if(userIds.isEmpty()) {
               return;
            }
            LambdaQueryWrapper<DocumentEntity> documentQW = new LambdaQueryWrapper<>();
            documentQW.in(DocumentEntity::getUploader, userIds);
            List<DocumentEntity> documentEntities = documentEntityMapper.selectList(documentQW);
            Optional.ofNullable(documentEntities).ifPresent(item -> {
                documentIds.addAll(item.stream().map(DocumentEntity::getId).toList());
            });
        });
        StringBuilder metaFilterSqlSb  = new StringBuilder();
        if (!userIds.isEmpty()) {
            String idsStr = userIds.stream().map(String::valueOf).collect(Collectors.joining(" , "));
            metaFilterSqlSb.append("user_id in [");
            metaFilterSqlSb.append(idsStr);
            metaFilterSqlSb.append("]");
        }

        if (!documentIds.isEmpty()) {
            if(!metaFilterSqlSb.isEmpty()) {
                metaFilterSqlSb.append(" OR ");
            }
            metaFilterSqlSb.append("document_id in [");
            String documentIdsStr = documentIds.stream().map(String::valueOf).collect(Collectors.joining(" , "));
            metaFilterSqlSb.append(documentIdsStr);
            metaFilterSqlSb.append("]");
        }

        // **如果 userIds 和 documentIds 都为空，则返回一个永远不匹配的条件 查询不到任何结果**
        if (metaFilterSqlSb.isEmpty()) {
            metaFilterSqlSb.append(" user_id = -1 ");
        }

        return metaFilterSqlSb.toString();
    }

    private String buildBaseAccessFilterByKnowledgeBase(String baseId){
        String s = buildBaseAccessFilter();
        String sb = s + " AND " +
                " knowledge_base_id = " + baseId;
        return sb;
    }
}
