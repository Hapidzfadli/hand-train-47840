package com.hand.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.api.dto.request.TaskImportDTO;
import com.hand.domain.entity.Task;
import com.hand.domain.repository.TaskRepository;
import io.choerodon.core.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.hzero.boot.imported.app.service.BatchImportHandler;
import org.hzero.boot.imported.infra.validator.annotation.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

@ImportService(templateCode = "DEMO-CLIENT-47840-BATCH")
@Slf4j
public class TaskBatchImportServiceImpl extends BatchImportHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Boolean doImport(List<String> data) {
        try {

            List<Task> taskList = new ArrayList<>();
            boolean hasError = false;

            for (int i = 0; i < data.size(); i++) {
                try {
                    TaskImportDTO taskDTO = objectMapper.readValue(data.get(i), TaskImportDTO.class);
                    Task task = new Task();
                    task.setEmployeeId(taskDTO.getEmployeeId());
                    task.setState(taskDTO.getState());
                    task.setTaskNumber(taskDTO.getTaskNumber());
                    task.setTaskDescription(taskDTO.getTaskDescription());
                    task.setTenantId(taskDTO.getTenantId());

                    taskList.add(task);
                } catch (Exception e) {
                    addErrorMsg(i, String.format("Error at row %d: %s", i + 1, e.getMessage()));
                    hasError = true;
                }
            }

            if (!hasError && !taskList.isEmpty()) {
                taskRepository.batchInsertSelective(taskList);
            }

            return !hasError;

        } catch (Exception e) {
            String errorMsg = String.format("Batch import failed: %s", e.getMessage());
            log.error(errorMsg, e);
            throw new CommonException(errorMsg);
        }
    }

    @Override
    public int getSize() {
        return 1000;
    }
}
