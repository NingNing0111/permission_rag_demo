package me.pgthinker.controller;

import lombok.RequiredArgsConstructor;
import me.pgthinker.common.BaseResponse;
import me.pgthinker.common.ResultUtils;
import me.pgthinker.model.vo.DocumentVO;
import me.pgthinker.service.DocumentEntityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Project: me.pgthinker.controller
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 23:03
 * @Description:
 */
@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {


    private final DocumentEntityService documentEntityService;

    @PostMapping(value="/file/upload",headers = "content-type=multipart/form-data")
    @PreAuthorize("hasAnyAuthority('upload_file')")
    public BaseResponse<DocumentVO> uploadDocument(@RequestParam("file") @NotNull(message = "文件不能为空") MultipartFile file, @RequestParam("knowledgeBaseId") @NotNull(message = "知识库不能为空") String knowledgeBaseId) {
        return ResultUtils.success(documentEntityService.uploadFile(file,knowledgeBaseId));
    }

//    @PostMapping("/managerList")
//    @PreAuthorize("hasAnyRole('admin')")
//    public BaseResponse<List<DocumentVO>> managerList() {
//        return ResultUtils.success()
//    }
}
