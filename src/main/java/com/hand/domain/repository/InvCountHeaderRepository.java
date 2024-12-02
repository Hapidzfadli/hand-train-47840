package com.hand.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import com.hand.domain.entity.InvCountHeader;

import java.util.Date;
import java.util.List;

/**
 * Inventory Count Header Table(InvCountHeader)资源库
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-11-28 11:04:15
 */
public interface InvCountHeaderRepository extends BaseRepository<InvCountHeader> {
    /**
     * 查询
     *
     * @param invCountHeader 查询条件
     * @return 返回值
     */
    List<InvCountHeader> selectList(InvCountHeader invCountHeader);

    /**
     * 根据主键查询（可关联表）
     *
     * @param countHeaderId 主键
     * @return 返回值
     */
    InvCountHeader selectByPrimary(Long countHeaderId);

    /**
     * Select by count number
     *
     * @param countNumber Count number
     * @return InvCountHeader
     */
    InvCountHeader selectCountNumber(String countNumber);

}
