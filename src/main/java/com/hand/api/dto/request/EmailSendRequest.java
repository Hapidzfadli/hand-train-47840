package com.hand.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Map;

@ApiModel("Email Send Request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailSendRequest {
    @ApiModelProperty("Context JSON")
    private Map<String, Object> contextJson;

    @ApiModelProperty("Email Address")
    @NotNull
    @Email
    private String emailAddress;

    @ApiModelProperty("Organization ID")
    @NotNull
    private Long organizationId;
}