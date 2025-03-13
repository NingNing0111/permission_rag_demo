package me.pgthinker.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.pgthinker.model.domain.KnowledgeBase;
import me.pgthinker.model.vo.KnowledgeBaseVO;

import java.util.List;

/**
 * @Project: me.pgthinker.service
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 22:01
 * @Description:
 */
public interface KnowledgeBaseService extends IService<KnowledgeBase> {


    String addKnowledgeBase(KnowledgeBaseVO knowledgeBaseVO);

    Integer removeKnowledgeBase(KnowledgeBaseVO knowledgeBaseVO);

    List<KnowledgeBaseVO> knowledgeList();
}
