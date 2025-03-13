package me.pgthinker.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName knowledge_base
 */
@TableName(value ="knowledge_base")
@Data
public class KnowledgeBase implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "description")
    private String description;

    @TableField(value = "creator")
    private Long creator;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}