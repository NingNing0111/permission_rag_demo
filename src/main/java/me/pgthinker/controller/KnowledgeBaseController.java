package me.pgthinker.controller;

import lombok.RequiredArgsConstructor;
import me.pgthinker.common.BaseResponse;
import me.pgthinker.common.ResultUtils;
import me.pgthinker.model.vo.KnowledgeBaseVO;
import me.pgthinker.service.KnowledgeBaseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Project: me.pgthinker.controller
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 22:00
 * @Description:
 */
@RestController
@RequestMapping("/knowledgeBase")
@RequiredArgsConstructor
public class KnowledgeBaseController {

    private final KnowledgeBaseService knowledgeBaseService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('admin')")
    public BaseResponse<String> addKnowledgeBase(@RequestBody KnowledgeBaseVO knowledgeBaseVO) {
        return ResultUtils.success(knowledgeBaseService.addKnowledgeBase(knowledgeBaseVO));
    }

    @PostMapping("/remove")
    @PreAuthorize("hasAnyRole('admin')")
    public BaseResponse<Integer> removeKnowledgeBase(@RequestBody KnowledgeBaseVO knowledgeBaseVO) {
        return ResultUtils.success(knowledgeBaseService.removeKnowledgeBase(knowledgeBaseVO));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('admin','leader','staff')")
    public BaseResponse<List<KnowledgeBaseVO>> listKnowledgeBase() {
        return ResultUtils.success(knowledgeBaseService.knowledgeList());
    }




}
