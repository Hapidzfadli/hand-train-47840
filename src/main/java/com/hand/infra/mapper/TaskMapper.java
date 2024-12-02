package com.hand.infra.mapper;

import com.hand.api.dto.request.TaskDTO;
import org.apache.ibatis.annotations.Param;
import io.choerodon.mybatis.common.BaseMapper;
import com.hand.domain.entity.Task;

import java.util.List;

/**
 * Task table(Task)应用服务
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-11-20 09:20:12
 */
public interface TaskMapper extends BaseMapper<Task> {
    /**
     * 基础查询
     *
     * @param task 查询条件
     * @return 返回值
     */
    List<Task> selectList(Task task);
    List<TaskDTO> selectListExport(TaskDTO task);

    /**
     * Check if employee exists
     * @param employeeId employee ID
     * @return count of employee
     */
    Integer checkEmployeeExists(@Param("employeeId") Long employeeId);
    List<Task> selectByEmployeeIds(@Param("employeeIds") List<Long> employeeIds);

}

