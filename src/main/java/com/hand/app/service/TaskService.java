package com.hand.app.service;

import com.hand.api.dto.request.TaskDTO;
import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import com.hand.domain.entity.Task;

import java.util.Arrays;
import java.util.List;

/**
 * Task table(Task)应用服务
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-11-20 09:20:13
 */
public interface TaskService {

    /**
     * 查询数据
     *
     * @param pageRequest 分页参数
     * @param tasks       查询条件
     * @return 返回值
     */
    Page<Task> selectList(PageRequest pageRequest, Task tasks);

    /**
     * 保存数据
     *
     * @param tasks 数据
     */
    void saveData(List<Task> tasks);

    List<TaskDTO> selectList(TaskDTO newTask);
}

