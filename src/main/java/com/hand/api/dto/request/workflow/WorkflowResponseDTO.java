package com.hand.api.dto.request.workflow;

import lombok.Data;
import org.hzero.boot.workflow.dto.RunTaskHistory;

import java.util.List;
@Data
public class WorkflowResponseDTO {
    private String status;
    private String message;
    private List<RunTaskHistory> approvalHistory;
}
