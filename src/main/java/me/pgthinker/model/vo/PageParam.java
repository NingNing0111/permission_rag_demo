package me.pgthinker.model.vo;

import lombok.Data;

/**
 * @Project: me.pgthinker.model.vo
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 23:10
 * @Description:
 */
@Data
public abstract class PageParam {
    private int page;
    private int pageSize;
}
