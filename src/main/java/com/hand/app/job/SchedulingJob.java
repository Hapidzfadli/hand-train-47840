package com.hand.app.job;

import com.hand.app.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.hzero.boot.scheduler.infra.annotation.JobHandler;
import org.hzero.boot.scheduler.infra.enums.ReturnT;
import org.hzero.boot.scheduler.infra.handler.IJobHandler;
import org.hzero.boot.scheduler.infra.tool.SchedulerTool;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@JobHandler("DEMO_JOB_47840")
@Slf4j
public class SchedulingJob implements IJobHandler {
    @Autowired
    private MessageService messageService;

    @Override
    public ReturnT execute(Map<String, String> map, SchedulerTool tool) {

        try {
            Long tenantId = Long.valueOf(map.get("tenantId"));
            messageService.sendScheduledMessage(tenantId);
            return ReturnT.SUCCESS;

        } catch (Exception e) {
            log.error("Job execution failed", e);
            return ReturnT.FAILURE;
        }
    }
}
