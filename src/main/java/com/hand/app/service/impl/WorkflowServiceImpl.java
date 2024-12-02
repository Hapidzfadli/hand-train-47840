package com.hand.app.service.impl;

import com.hand.api.dto.request.workflow.WorkflowEventRequestDTO;
import com.hand.api.dto.request.workflow.WorkflowResponseDTO;
import com.hand.app.service.WorkflowService;
import com.hand.domain.entity.InvCountHeader;
import com.hand.domain.repository.InvCountHeaderRepository;
import com.hand.infra.constant.WorkFlowConstant;
import io.choerodon.core.exception.CommonException;
import org.hzero.boot.workflow.WorkflowClient;
import org.hzero.boot.workflow.dto.RunInstance;
import org.hzero.boot.workflow.dto.RunTaskHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkflowServiceImpl implements WorkflowService {

    @Autowired
    private WorkflowClient workflowClient;
    @Autowired
    private InvCountHeaderRepository invCountHeaderRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RunInstance startWorkflow(Long tenantId, WorkflowEventRequestDTO request) {
        try {

            InvCountHeader invCountHeader = invCountHeaderRepository.selectCountNumber(
                    request.getBusinessKey());

            if (invCountHeader == null) {
                throw new CommonException(WorkFlowConstant.MsgError.ERROR_DOCUMENT_NOT_FOUND);
            }

            return workflowClient.startInstanceByFlowKey(
                    tenantId,
                    request.getFlowKey(),
                    request.getBusinessKey(),
                    request.getDimension(),
                    request.getStarter(),
                    request.getVariables()
            );
        } catch (Exception e) {
            throw new CommonException(WorkFlowConstant.MsgError.ERROR_WORKFLOW_START);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WorkflowResponseDTO withdrawWorkflow(Long tenantId, WorkflowEventRequestDTO request) {
        try {

            workflowClient.flowWithdrawFlowKey(
                    tenantId,
                    request.getFlowKey(),
                    request.getBusinessKey()
            );

            WorkflowResponseDTO response = new WorkflowResponseDTO();
            response.setStatus("SUCCESS");
            response.setMessage("Workflow withdrawn successfully");
            return response;
        } catch (Exception e) {
            throw new CommonException(WorkFlowConstant.MsgError.ERROR_WORKFLOW_WITHDRAW);
        }
    }

    @Override
    public WorkflowResponseDTO getApprovalHistory(Long tenantId, Long instanceId) {
        try {
            List<RunTaskHistory> approvalHistory = workflowClient.approveHistory(
                    tenantId,
                    instanceId
            );

            WorkflowResponseDTO response = new WorkflowResponseDTO();
            response.setStatus("SUCCESS");
            response.setMessage("Approval history retrieved successfully");
            response.setApprovalHistory(approvalHistory);
            return response;
        } catch (Exception e) {
            throw new CommonException(WorkFlowConstant.MsgError.ERROR_WORKFLOW_HISTORY);
        }
    }
}
