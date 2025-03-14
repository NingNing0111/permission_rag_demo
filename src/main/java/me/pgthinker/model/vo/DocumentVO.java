package me.pgthinker.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Project: me.pgthinker.model.vo
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 23:04
 * @Description:
 */
@Data
public class DocumentVO {
    private Long id;

    private String fileName;

    private String path;

    private Long uploader;
}
