package me.pgthinker.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.pgthinker.common.ErrorCode;
import me.pgthinker.exception.BusinessException;
import me.pgthinker.mapper.KnowledgeBaseMapper;
import me.pgthinker.mapper.UserMapper;
import me.pgthinker.model.domain.KnowledgeBase;
import me.pgthinker.model.domain.Role;
import me.pgthinker.model.domain.User;
import me.pgthinker.model.vo.KnowledgeBaseVO;
import me.pgthinker.security.UserDetailsImpl;
import me.pgthinker.service.KnowledgeBaseService;
import me.pgthinker.utils.SecurityFrameworkUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Project: me.pgthinker.service.impl
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 22:02
 * @Description:
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class KnowledgeBaseServiceImpl extends ServiceImpl<KnowledgeBaseMapper, KnowledgeBase> implements KnowledgeBaseService {

    private final UserMapper userMapper;

    @Override
    public String addKnowledgeBase(KnowledgeBaseVO knowledgeBaseVO) {
        String name = knowledgeBaseVO.getName();
        String description = knowledgeBaseVO.getDescription();
        KnowledgeBase knowledgeBase = new KnowledgeBase();
        knowledgeBase.setName(name);
        knowledgeBase.setDescription(description);
        knowledgeBase.setCreator(SecurityFrameworkUtil.getCurrUserId());
        this.save(knowledgeBase);
        return knowledgeBase.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer removeKnowledgeBase(KnowledgeBaseVO knowledgeBaseVO) {
        String id = knowledgeBaseVO.getId();
        if(StringUtils.isEmpty(id)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return this.removeById(id) ? 1 : 0;
    }

    @Override
    public List<KnowledgeBaseVO> knowledgeList() {
        UserDetailsImpl loginUser = SecurityFrameworkUtil.getLoginUser();
        Optional.ofNullable(loginUser).orElseThrow(() -> new BusinessException(ErrorCode.PARAMS_ERROR));
        User userWithRolesAndPermissions = userMapper.getUserWithRolesAndPermissions(loginUser.getUsername());
        List<Role> roles = userWithRolesAndPermissions.getRoles();
        List<String> roleNames = roles.stream().map(Role::getName).toList();
        LambdaQueryWrapper<KnowledgeBase> qw = new LambdaQueryWrapper<>();
        if(!roleNames.contains("admin")) {
            qw.eq(KnowledgeBase::getCreator, SecurityFrameworkUtil.getCurrUserId());
        }
        List<KnowledgeBase> list = this.list(qw);
        return transfer(list);
    }

    private List<KnowledgeBaseVO> transfer(List<KnowledgeBase> list) {
        return list.stream().map(item -> {
            KnowledgeBaseVO knowledgeBaseVO = new KnowledgeBaseVO();
            BeanUtils.copyProperties(item, knowledgeBaseVO);
            return knowledgeBaseVO;
        }).toList();
    }
}
