package com.hand.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hand.app.service.TaskService;
import com.hand.domain.entity.Task;
import com.hand.domain.repository.TaskRepository;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Task table(Task)表控制层
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-11-20 09:20:13
 */

@RestController("taskController.v1")
@RequestMapping("/v1/{organizationId}/tasks")
public class TaskController extends BaseController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "Task table列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    public ResponseEntity<Page<Task>> list(Task task, @PathVariable Long organizationId,
                                           @ApiIgnore @SortDefault(value = Task.FIELD_ID,
                                                   direction = Sort.Direction.DESC) PageRequest pageRequest) {
        task.setTenantId(organizationId);
        Page<Task> list = taskService.selectList(pageRequest, task);
        return Results.success(list);
    }

    @ApiOperation(value = "Task table明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/{organizationId}/detail")
    public ResponseEntity<Task> detail(@PathVariable Long organizationId) {
        Task task = taskRepository.selectByPrimary(organizationId);
        return Results.success(task);
    }

    @ApiOperation(value = "创建或更新Task table")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<List<Task>> save(@PathVariable Long organizationId, @RequestBody List<Task> tasks) {
        validObject(tasks);
        SecurityTokenHelper.validTokenIgnoreInsert(tasks);
        tasks.forEach(item -> item.setTenantId(organizationId));
        taskService.saveData(tasks);
        return Results.success(tasks);
    }

    @ApiOperation(value = "删除Task table")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody List<Task> tasks) {
        SecurityTokenHelper.validToken(tasks);
        taskRepository.batchDeleteByPrimaryKey(tasks);
        return Results.success();
    }

}

