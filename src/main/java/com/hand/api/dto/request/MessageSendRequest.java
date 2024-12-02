package com.hand.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("Message Send Request")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageSendRequest {
    @ApiModelProperty("List of messages")
    private List<String> messages;

    @ApiModelProperty("Receiver ID")
    @NotNull
    private Long receiverId;

    @ApiModelProperty("Organization ID")
    @NotNull
    private Long organizationId;
}