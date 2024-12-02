package com.hand.infra.repository.impl;

import org.apache.commons.collections.CollectionUtils;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Component;
import com.hand.domain.entity.InvCountHeader;
import com.hand.domain.repository.InvCountHeaderRepository;
import com.hand.infra.mapper.InvCountHeaderMapper;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Inventory Count Header Table(InvCountHeader)资源库
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-11-28 11:04:15
 */
@Component
public class InvCountHeaderRepositoryImpl extends BaseRepositoryImpl<InvCountHeader> implements InvCountHeaderRepository {
    @Resource
    private InvCountHeaderMapper invCountHeaderMapper;

    @Override
    public List<InvCountHeader> selectList(InvCountHeader invCountHeader) {
        return invCountHeaderMapper.selectList(invCountHeader);
    }

    @Override
    public InvCountHeader selectByPrimary(Long countHeaderId) {
        InvCountHeader invCountHeader = new InvCountHeader();
        invCountHeader.setCountHeaderId(countHeaderId);
        List<InvCountHeader> invCountHeaders = invCountHeaderMapper.selectList(invCountHeader);
        if (invCountHeaders.size() == 0) {
            return null;
        }
        return invCountHeaders.get(0);
    }

    @Override
    public InvCountHeader selectCountNumber(String countNumber) {
        InvCountHeader invCountHeader = new InvCountHeader();
        invCountHeader.setCountNumber(countNumber);
        List<InvCountHeader> invCountHeaders = invCountHeaderMapper.selectList(invCountHeader);
        if (CollectionUtils.isEmpty(invCountHeaders)) {
            return null;
        }
        return invCountHeaders.get(0);
    }

}

