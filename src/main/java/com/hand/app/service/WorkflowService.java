package com.hand.app.service;

import com.hand.api.dto.request.workflow.WorkflowEventRequestDTO;
import com.hand.api.dto.request.workflow.WorkflowResponseDTO;
import org.hzero.boot.workflow.dto.RunInstance;

public interface WorkflowService {
    /**
     * Start workflow process
     *
     * @param tenantId tenant ID
     * @param request workflow request
     * @return workflow response
     */
    RunInstance startWorkflow(Long tenantId, WorkflowEventRequestDTO request);

    /**
     * Withdraw workflow process
     *
     * @param tenantId tenant ID
     * @param request workflow request
     * @return workflow response
     */
    WorkflowResponseDTO withdrawWorkflow(Long tenantId, WorkflowEventRequestDTO request);

    /**
     * Get approval history
     *
     * @param tenantId tenant ID
     * @param instanceId instance ID
     * @return workflow response with approval history
     */
    WorkflowResponseDTO getApprovalHistory(Long tenantId, Long instanceId);
}
