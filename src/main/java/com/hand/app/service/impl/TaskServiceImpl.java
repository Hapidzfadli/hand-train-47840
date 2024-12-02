package com.hand.app.service.impl;

import com.hand.api.dto.request.TaskDTO;
import com.hand.infra.constant.TaskConstant;
import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.hzero.boot.platform.lov.adapter.LovAdapter;
import org.hzero.boot.platform.lov.dto.LovValueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.hand.app.service.TaskService;
import org.springframework.stereotype.Service;
import com.hand.domain.entity.Task;
import com.hand.domain.repository.TaskRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Task table(Task)应用服务
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-11-20 09:20:13
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CodeRuleBuilder codeRuleBuilder;

    @Autowired
    private LovAdapter lovAdapter;

    @Override
    public Page<Task> selectList(PageRequest pageRequest, Task task) {
        return PageHelper.doPageAndSort(pageRequest, () -> taskRepository.selectList(task));
    }

    @Override
    public List<TaskDTO> selectList(TaskDTO newTask) {
        return taskRepository.selectList(newTask);
    }

    @Override
    public void saveData(List<Task> tasks) {
        List<Task> insertList = tasks.stream().filter(line -> line.getId() == null).collect(Collectors.toList());
        List<Task> updateList = tasks.stream().filter(line -> line.getId() != null).collect(Collectors.toList());
        insertList.forEach(this::generateTaskNumber);
        tasks.forEach(this::validateTaskType);

        taskRepository.batchInsertSelective(insertList);
        taskRepository.batchUpdateByPrimaryKeySelective(updateList);
    }

    private void generateTaskNumber(Task task) {
        String ruleCode = TaskConstant.TaskConstantConfig.RULE_CODE;
        Map<String, String> variableMap = new HashMap<>();
        String currentUserName = DetailsHelper.getUserDetails().getRealName();
        variableMap.put("customSegment", currentUserName);

        String taskNumber = codeRuleBuilder.generateCode(ruleCode, variableMap);
        taskNumber = taskNumber.replaceAll("\\s+", "-");
        task.setTaskNumber(taskNumber);
    }

    private void validateTaskType(Task task) {
        List<LovValueDTO> taskTypes = lovAdapter.queryLovValue(TaskConstant.TaskConstantConfig.VALUE_SET_CODE, task.getTenantId());
        boolean isValid = taskTypes.stream()
                .anyMatch(lovValue -> lovValue.getValue().equals(task.getTaskType()));

        if (!isValid) {
            throw new CommonException(TaskConstant.MsgError.ERROR_INVALID_TASK_TYPE, task.getTaskType());
        }
    }
}