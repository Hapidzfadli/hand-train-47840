package com.hand.api.controller.v1;

import com.hand.api.dto.request.EmailSendRequest;
import com.hand.api.dto.request.MessageSendRequest;
import com.hand.api.dto.response.MessageResponse;
import com.hand.app.service.MessageService;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("messageController.v1")
@RequestMapping("/v1/messages")
@Api(tags = "Message Management")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/station")
    @Permission(level = ResourceLevel.SITE)
    @ApiOperation(value = "Send Station Message")
    public ResponseEntity<MessageResponse> sendStationMessage(
            @RequestBody @Validated MessageSendRequest request) {
        return ResponseEntity.ok(messageService.sendStationMessage(request));
    }

    @PostMapping("/email")
    @Permission(level = ResourceLevel.SITE)
    @ApiOperation(value = "Send Email")
    public ResponseEntity<MessageResponse> sendEmail(
            @RequestBody @Validated EmailSendRequest request) {
        return ResponseEntity.ok(messageService.sendEmail(request));
    }
}