package com.hand.domain.validators;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.api.dto.request.TaskImportDTO;
import com.hand.domain.repository.TaskRepository;
import com.hand.infra.constant.TodoTaskConstant;
import lombok.extern.slf4j.Slf4j;
import org.hzero.boot.imported.app.service.BatchValidatorHandler;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidator;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidators;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@ImportValidators({
        @ImportValidator(templateCode = "DEMO-CLIENT-47840"),
        @ImportValidator(templateCode = "DEMO-CLIENT-47840-BATCH")
})
@Slf4j
public class TaskImportValidator extends BatchValidatorHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public boolean validate(List<String> data) {
        boolean isValid = true;

        for (int i = 0; i < data.size(); i++) {
            try {
                TaskImportDTO taskDTO = objectMapper.readValue(data.get(i), TaskImportDTO.class);

                if (!taskDTO.getTaskNumber().matches("^[a-zA-Z0-9]+$")) {
                    addErrorMsg(i, TodoTaskConstant.ErrorMessage.ERROR_MESSAGE_TASK_NUMBER_INVALID);
                    isValid = false;
                    continue;
                }

                if (!taskRepository.existsByEmployeeId(taskDTO.getEmployeeId())) {
                    addErrorMsg(i, String.format(TodoTaskConstant.ErrorMessage.ERROR_MESSAGE_EMPLOYEE_NOT_FOUND,
                            taskDTO.getEmployeeId()));
                    isValid = false;
                    continue;
                }

            } catch (Exception e) {
                log.error("Error validating row {}: {}", i, e.getMessage());
                addErrorMsg(i, "Error: " + e.getMessage());
                isValid = false;
            }
        }

        return isValid;
    }

    @Override
    public int getSize() {
        return 1000;
    }
}