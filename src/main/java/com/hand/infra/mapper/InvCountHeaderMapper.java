package com.hand.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import com.hand.domain.entity.InvCountHeader;

import java.util.List;

/**
 * Inventory Count Header Table(InvCountHeader)应用服务
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-11-28 11:04:14
 */
public interface InvCountHeaderMapper extends BaseMapper<InvCountHeader> {
    /**
     * 基础查询
     *
     * @param invCountHeader 查询条件
     * @return 返回值
     */
    List<InvCountHeader> selectList(InvCountHeader invCountHeader);
}

