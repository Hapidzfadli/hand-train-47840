package com.hand.app.service.impl;

import com.hand.api.dto.request.TaskDTO;
import com.hand.api.dto.request.UserDTO;
import com.hand.app.service.TaskService;
import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.hand.app.service.UserService;
import org.springframework.stereotype.Service;
import com.hand.domain.entity.User;
import com.hand.domain.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * User Table(User)应用服务
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-12-02 15:17:34
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskService taskService;

    @Override
    public Page<User> selectList(PageRequest pageRequest, User user) {
        return PageHelper.doPageAndSort(pageRequest, () -> userRepository.selectList(user));
    }

    @Override
    public void saveData(List<User> users) {
        List<User> insertList = users.stream().filter(line -> line.getId() == null).collect(Collectors.toList());
        List<User> updateList = users.stream().filter(line -> line.getId() != null).collect(Collectors.toList());
        userRepository.batchInsertSelective(insertList);
        userRepository.batchUpdateByPrimaryKeySelective(updateList);
    }

    @Override
    public List<UserDTO> exportData(UserDTO userDTO) {
        List<UserDTO> userList = userRepository.selectList(userDTO);
        List<String> employeeIdList = userList.stream().map(UserDTO::getEmployeeNumber).collect(Collectors.toList());

        TaskDTO newTask = new TaskDTO();
        newTask.setEmpIdList(employeeIdList.stream().map(Long::valueOf).collect(Collectors.toList()));
        List<TaskDTO> taskList = taskService.selectList(newTask);

        Map<Long, List<TaskDTO>> taskMap = taskList.stream()
                .collect(Collectors.groupingBy(TaskDTO::getEmployeeId));

        userList.forEach(user -> user.setTaskList(taskMap.getOrDefault(Long.valueOf(user.getEmployeeNumber()), new ArrayList<>())));

        return userList;
    }
}

