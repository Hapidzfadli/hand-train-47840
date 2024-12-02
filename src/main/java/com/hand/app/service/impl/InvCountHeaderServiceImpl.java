package com.hand.app.service.impl;

import com.hand.api.dto.request.InvCountHeaderDTO;
import com.hand.api.dto.request.workflow.WorkflowApprovalRequestDTO;
import com.hand.infra.constant.WorkFlowConstant;
import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.hand.app.service.InvCountHeaderService;
import org.springframework.stereotype.Service;
import com.hand.domain.entity.InvCountHeader;
import com.hand.domain.repository.InvCountHeaderRepository;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Inventory Count Header Table(InvCountHeader)应用服务
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-11-28 11:04:15
 */
@Slf4j
@Service
public class InvCountHeaderServiceImpl implements InvCountHeaderService {
    @Autowired
    private InvCountHeaderRepository invCountHeaderRepository;

    @Override
    public Page<InvCountHeader> selectList(PageRequest pageRequest, InvCountHeader invCountHeader) {
        return PageHelper.doPageAndSort(pageRequest, () -> invCountHeaderRepository.selectList(invCountHeader));
    }

    @Override
    public void saveData(List<InvCountHeader> invCountHeaders) {
        List<InvCountHeader> insertList = invCountHeaders.stream().filter(line -> line.getCountHeaderId() == null).collect(Collectors.toList());
        List<InvCountHeader> updateList = invCountHeaders.stream().filter(line -> line.getCountHeaderId() != null).collect(Collectors.toList());
        invCountHeaderRepository.batchInsertSelective(insertList);
        invCountHeaderRepository.batchUpdateByPrimaryKeySelective(updateList);
    }

    @Override
    public InvCountHeaderDTO approvalCallback(Long organizationId, WorkflowApprovalRequestDTO workFlowEventRequest) {

        InvCountHeader invCountHeader = invCountHeaderRepository.selectCountNumber(
                workFlowEventRequest.getBusinessKey());

        if (invCountHeader == null) {
            throw new CommonException(WorkFlowConstant.MsgError.ERROR_DOCUMENT_NOT_FOUND);
        }

        InvCountHeaderDTO invCountHeaderDTO = new InvCountHeaderDTO();
        BeanUtils.copyProperties(invCountHeader, invCountHeaderDTO);

        invCountHeader.setWorkflowId(workFlowEventRequest.getWorkflowId());
        invCountHeader.setCountStatus(workFlowEventRequest.getDocStatus());

        if (WorkFlowConstant.DocStatus.COMPLETED.equals(workFlowEventRequest.getDocStatus())) {
            invCountHeader.setApprovedTime(workFlowEventRequest.getApprovedTime());
        }

        invCountHeaderRepository.updateOptional(invCountHeader,
                InvCountHeader.FIELD_WORKFLOW_ID,
                InvCountHeader.FIELD_COUNT_STATUS,
                InvCountHeader.FIELD_APPROVED_TIME);

        BeanUtils.copyProperties(invCountHeader, invCountHeaderDTO);
        return invCountHeaderDTO;
    }
}

