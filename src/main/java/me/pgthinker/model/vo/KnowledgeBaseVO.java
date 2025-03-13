package me.pgthinker.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @Project: me.pgthinker.model.vo
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 22:04
 * @Description:
 */
@Data
public class KnowledgeBaseVO {
    private String id;

    @NotBlank(message = "知识库名称不能为空")
    private String name;

    private String description;
}
