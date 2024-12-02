package com.hand.app.service.impl;

import com.hand.api.dto.request.EmailSendRequest;
import com.hand.api.dto.request.MessageSendRequest;
import com.hand.api.dto.response.MessageResponse;
import com.hand.app.service.MessageService;
import com.hand.domain.entity.User;
import com.hand.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hzero.boot.message.MessageClient;
import org.hzero.boot.message.entity.FlyBookMsgType;
import org.hzero.boot.message.entity.Message;
import org.hzero.boot.message.entity.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    private static final String TEMPLATE_CODE = "TEST-47840";
    private static final String TEMPLATE_CODE_STATION = "TEST-STATION-47840";
    private static final String TEMPLATE_CODE_SCHUDLE = "TEST-FEISHU-47840";
    private static final String SERVER_CODE = "DEMO-47840";
    private static final String SERVER_CODE_FEISU = "FEIYU";
    private static final String SENDER_NAME = "HAPID";
    private static final String SENDER_EMAIL = "hapid.fadli@hand-global.com";
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);


    @Autowired
    private MessageClient messageClient;
    @Autowired
    private UserRepository userRepository;

    @Override
    public MessageResponse sendStationMessage(MessageSendRequest messageSendRequest) {
        try {

            Receiver receiver = new Receiver()
                    .setUserId(messageSendRequest.getReceiverId())
                    .setTargetUserTenantId(messageSendRequest.getOrganizationId());

            List<String> messages = messageSendRequest.getMessages();
            List<String> finalMessages = processMessages(messages);

            Map<String, String> params = new HashMap<>();
            params.put("message1", finalMessages.get(0));
            params.put("message2", finalMessages.get(1));
            params.put("senderName", SENDER_NAME);

            log.info("Receiver: {}", Collections.singletonList(receiver));

            Message result = messageClient.sendWebMessage(
                    messageSendRequest.getOrganizationId(),
                    TEMPLATE_CODE_STATION,
                    "en_US",
                    Collections.singletonList(receiver),
                    params
            );

            return MessageResponse.success("Station message sent successfully", result);

        } catch (Exception e) {
            return MessageResponse.error("Failed to send station message: " + e.getMessage());
        }
    }

    @Override
    public MessageResponse sendEmail(EmailSendRequest emailDTO) {
        try {

            Receiver receiver = new Receiver()
                    .setEmail(emailDTO.getEmailAddress())
                    .setTargetUserTenantId(emailDTO.getOrganizationId());

            Map<String, String> params = new HashMap<>();

            if (emailDTO.getContextJson() != null) {
                params.putAll(convertMapToStringMap(emailDTO.getContextJson()));
            }

            Message result = messageClient.sendEmail(
                    emailDTO.getOrganizationId(),
                    SERVER_CODE,
                    TEMPLATE_CODE,
                    "en_US",
                    Collections.singletonList(receiver),
                    params
            );

            return MessageResponse.success("Email sent successfully", result);

        } catch (Exception e) {
            return MessageResponse.error("Failed to send email: " + e.getMessage());
        }
    }

    @Override
    public void sendScheduledMessage(Long tenantId) {
        try {
            User user = userRepository.selectByPrimary(1L);
            log.info("Sending scheduled message for user: {}", user);

            sendFlybook(tenantId, user);

            log.info("Scheduled message sent successfully to user");

        } catch (Exception e) {
            log.error("Failed to send scheduled message", e);
            throw new RuntimeException("Failed to send scheduled message: " + e.getMessage());
        }
    }


    @Override
    public void sendConcurrentMessages(Long tenantId) {
        try {

            List<User> users = userRepository.selectAll();
            log.info(" : {}", users);
            log.info("Sending concurrent messages to {} users", users.size());

            users.forEach(user ->
                    CompletableFuture.runAsync(() -> {
                        try {
                            sendFlybook(tenantId, user);
                            log.info("Concurrent message sent successfully to user: {}", user.getEmployeeName());
                        } catch (Exception e) {
                            log.error("Failed to send concurrent message to user: {}", user.getEmployeeName(), e);
                        }
                    }, executorService)
            );

        } catch (Exception e) {
            log.error("Failed to send concurrent messages", e);
            throw new RuntimeException("Failed to send concurrent messages: " + e.getMessage());
        }
    }

    private List<String> processMessages(List<String> messages) {
        List<String> defaultMessages = Arrays.asList(
                "Welcome to HZERO platform",
                "Thank you for using our service"
        );

        if (messages == null || messages.isEmpty()) {
            return defaultMessages;
        }

        List<String> result = new ArrayList<>();
        result.add(messages.get(0));

        if (messages.size() > 1 && messages.get(1) != null) {
            result.add(messages.get(1));
        } else {
            result.add("default");
        }

        return result;
    }

    private Map<String, String> convertMapToStringMap(Map<String, Object> map) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return result;
    }

    private void sendFlybook(Long tenantId, User user) {
        Map<String, String> receiverMap = new HashMap<>();
        receiverMap.put("email", "shaoqin.zhou@hand-china.com");

        Map<String, Object> params = new HashMap<>();
        params.put("employee_name", user.getEmployeeName());
        params.put("employee_number", user.getEmployeeNumber());
        params.put("email", user.getEmail());


        Message result = messageClient.sendFlyBook(
                tenantId,
                SERVER_CODE_FEISU,
                TEMPLATE_CODE_SCHUDLE,
                FlyBookMsgType.TEXT,
                "en_US",
                Collections.singletonList(receiverMap),
                params
        );

        log.info("result : {}", result.getSendFlag());
    }


}