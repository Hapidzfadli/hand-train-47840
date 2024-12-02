package com.hand.api.dto.request;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvCountHeaderDTO {
    @ApiModelProperty("Count Header Id")
    private Long countHeaderId;

    @ApiModelProperty("Tenant Id")
    private Long tenantId;

    @ApiModelProperty("Count Number")
    private String countNumber;

    @ApiModelProperty("Count Status")
    private String countStatus;

    @ApiModelProperty("Count Type")
    private String countType;

    @ApiModelProperty("Count Mode")
    private String countMode;

    @ApiModelProperty("Counter IDs")
    private Object countorIds;

    @ApiModelProperty("Supervisor IDs")
    private Object supervisorIds;

    @ApiModelProperty("Workflow Id")
    private Long workflowId;

    @ApiModelProperty("Approved Time")
    private Date approvedTime;

    @ApiModelProperty("Remark")
    private Object remark;
}