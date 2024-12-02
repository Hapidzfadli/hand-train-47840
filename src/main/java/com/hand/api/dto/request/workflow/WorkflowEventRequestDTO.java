package com.hand.api.dto.request.workflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Map;

@Data
public class WorkflowEventRequestDTO {
    @NotBlank
    @JsonProperty("businessKey")
    private String businessKey;

    @NotBlank
    @JsonProperty("flowKey")
    private String flowKey;

    @Pattern(regexp = "^(EMPLOYEE|USER)")
    @JsonProperty(defaultValue = "EMPLOYEE")
    private String dimension;

    @JsonProperty(defaultValue = "47840")
    private String starter;

    private String action;
    private String comment;
    private Map<String, Object> variables;
}
