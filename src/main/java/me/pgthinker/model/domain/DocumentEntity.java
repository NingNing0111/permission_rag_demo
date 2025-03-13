package me.pgthinker.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName document_entity
 */
@TableName(value ="document_entity")
@Data
public class DocumentEntity implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "file_name")
    private String fileName;

    /**
     * 
     */
    @TableField(value = "path")
    private String path;

    /**
     * 
     */
    @TableField(value = "uploader")
    private Long uploader;

    /**
     * 
     */
    @TableField(value = "base_id")
    private Object baseId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}