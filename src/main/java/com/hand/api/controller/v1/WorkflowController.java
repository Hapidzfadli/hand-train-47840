package com.hand.api.controller.v1;

import com.hand.api.dto.request.workflow.WorkflowEventRequestDTO;
import com.hand.api.dto.request.workflow.WorkflowResponseDTO;
import com.hand.app.service.WorkflowService;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import org.hzero.boot.workflow.dto.RunInstance;
import org.hzero.core.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("workflowController.v1")
@RequestMapping("/v1/{organizationId}/workflow")
public class WorkflowController {
    @Autowired
    private WorkflowService workflowService;

    @ApiOperation(value = "Start Workflow Process")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/start")
    public ResponseEntity<RunInstance> startWorkflow(
            @PathVariable Long organizationId,
            @RequestBody WorkflowEventRequestDTO request) {
        return Results.success(workflowService.startWorkflow(organizationId, request));
    }

    @ApiOperation(value = "Withdraw Workflow Process")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/withdraw")
    public ResponseEntity<WorkflowResponseDTO> withdrawWorkflow(
            @PathVariable Long organizationId,
            @RequestBody WorkflowEventRequestDTO request) {
        return Results.success(workflowService.withdrawWorkflow(organizationId, request));
    }

    @ApiOperation(value = "Get Approval History")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/approval-history")
    public ResponseEntity<WorkflowResponseDTO> getApprovalHistory(
            @PathVariable Long organizationId,
            @RequestParam("instanceId") Long instanceId) {
        return Results.success(workflowService.getApprovalHistory(organizationId, instanceId));
    }
}
