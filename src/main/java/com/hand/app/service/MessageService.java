package com.hand.app.service;

import com.hand.api.dto.request.EmailSendRequest;
import com.hand.api.dto.request.MessageSendRequest;
import com.hand.api.dto.response.MessageResponse;

import java.util.Map;

public interface MessageService {
    /**
     * Send station message
     * @param request Message send request
     * @return Message response
     */
    MessageResponse sendStationMessage(MessageSendRequest request);

    /**
     * Send email
     * @param request Email send request
     * @return Message response
     */
    MessageResponse sendEmail(EmailSendRequest request);

    void sendScheduledMessage(Long tenantId);
    void sendConcurrentMessages(Long tenantId);
}
