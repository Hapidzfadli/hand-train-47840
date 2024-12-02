package com.hand.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.api.dto.request.TaskImportDTO;
import com.hand.domain.entity.Task;
import com.hand.domain.repository.TaskRepository;
import io.choerodon.core.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.hzero.boot.imported.app.service.ImportHandler;
import org.hzero.boot.imported.infra.validator.annotation.ImportService;
import org.springframework.beans.factory.annotation.Autowired;


@ImportService(templateCode = "DEMO-CLIENT-47840")
@Slf4j
public class TaskImportServiceImpl extends ImportHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Boolean doImport(String data){
        try {
            log.info("data : {}", data);
            TaskImportDTO taskDTO = objectMapper.readValue(data, TaskImportDTO.class);

            Task task = new Task();

            task.setEmployeeId(taskDTO.getEmployeeId());
            task.setState(taskDTO.getState());
            task.setTaskNumber(taskDTO.getTaskNumber());
            task.setTaskDescription(taskDTO.getTaskDescription());
            task.setTenantId(taskDTO.getTenantId());

            taskRepository.insertSelective(task);
            return true;

        } catch (Exception e){
            getContext().addErrorMsg(e.getMessage());
            throw new CommonException(e);
        }
    }
}
