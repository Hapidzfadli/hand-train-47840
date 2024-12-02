package com.hand.api.dto.request.workflow;

import lombok.Data;

import java.util.Date;

@Data
public class WorkflowApprovalRequestDTO {
    private String businessKey;
    private Long workflowId;
    private String docStatus;
    private Date approvedTime;
}
