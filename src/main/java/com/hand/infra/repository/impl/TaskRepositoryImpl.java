package com.hand.infra.repository.impl;

import com.hand.api.dto.request.TaskDTO;
import com.hand.domain.entity.Task;
import com.hand.domain.repository.TaskRepository;
import com.hand.infra.mapper.TaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.mybatis.common.Criteria;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Task table(Task) resource repository
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-11-20 09:20:13
 */
@Component
@Slf4j
public class TaskRepositoryImpl extends BaseRepositoryImpl<Task> implements TaskRepository {
    @Resource
    private TaskMapper taskMapper;

    @Override
    public List<Task> selectList(Task task) {
        return taskMapper.selectList(task);
    }

    @Override
    public List<TaskDTO> selectList(TaskDTO task) {
        return taskMapper.selectListExport(task);
    }

    @Override
    public Task selectByPrimary(Long id) {
        Task task = new Task();
        task.setId(id);
        List<Task> tasks = taskMapper.selectList(task);
        if (tasks.size() == 0) {
            return null;
        }
        return tasks.get(0);
    }

    @Override
    public List<Task> selectByEmployeeId(Long employeeId) {
        Task task = new Task();
        task.setEmployeeId(employeeId);
        return this.selectOptional(task, new Criteria()
                .select(Task.FIELD_ID, Task.FIELD_EMPLOYEE_ID, Task.FIELD_STATE, Task.FIELD_TASK_DESCRIPTION)
                .where(Task.FIELD_EMPLOYEE_ID)
        );
    }

    @Override
    public Task selectDetailByTaskNumber(String taskNumber) {
        Task params = new Task();
        params.setTaskNumber(taskNumber);
        List<Task> tasks = taskMapper.selectList(params);
        return CollectionUtils.isNotEmpty(tasks) ? tasks.get(0) : null;
    }

    @Override
    public boolean existsByEmployeeId(Long employeeId) {
        if (employeeId == null) {
            return false;
        }
        Integer count = taskMapper.checkEmployeeExists(employeeId);
        log.info("Checking employee existence for ID: {}, exists: {}", employeeId, count > 0);
        return count > 0;
    }

    @Override
    public List<Task> selectByEmployeeIds(List<Long> employeeIds) {
        return taskMapper.selectByEmployeeIds(employeeIds);
    }
}