package com.hand.domain.repository;

import com.hand.api.dto.request.TaskDTO;
import com.hand.domain.entity.Task;
import org.hzero.mybatis.base.BaseRepository;

import java.util.List;

/**
 * Task table(Task) resource repository
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-11-20 09:20:12
 */
public interface TaskRepository extends BaseRepository<Task> {
    List<Task> selectList(Task task);

    List<TaskDTO> selectList(TaskDTO task);
    Task selectByPrimary(Long id);

    List<Task> selectByEmployeeId(Long userId);

    Task selectDetailByTaskNumber(String taskNumber);

    boolean existsByEmployeeId(Long employeeId);

    List<Task> selectByEmployeeIds(List<Long> employeeIds);
}