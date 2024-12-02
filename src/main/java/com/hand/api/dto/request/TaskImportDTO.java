package com.hand.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TaskImportDTO {
    @JsonProperty ("employeeId")
    private Long employeeId;

    @JsonProperty("state")
    private String state;

    @JsonProperty("taskNumber")
    private String taskNumber;

    @JsonProperty("taskDescription")
    private String taskDescription;

    @JsonProperty("tenantId")
    private Long tenantId;
}
