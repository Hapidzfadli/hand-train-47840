package com.hand.api.controller.v1;

import com.hand.api.dto.request.UserDTO;
import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hand.app.service.UserService;
import com.hand.domain.entity.User;
import com.hand.domain.repository.UserRepository;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User Table(User)表控制层
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-12-02 15:17:34
 */

@RestController("userController.v1")
@RequestMapping("/v1/{organizationId}/users")
public class UserController extends BaseController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "User Table列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    public ResponseEntity<Page<User>> list(User user, @PathVariable Long organizationId,
                                           @ApiIgnore @SortDefault(value = User.FIELD_ID,
                                                   direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<User> list = userService.selectList(pageRequest, user);
        return Results.success(list);
    }

    @ApiOperation(value = "User Table明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/{id}/detail")
    public ResponseEntity<User> detail(@PathVariable Long id) {
        User user = userRepository.selectByPrimary(id);
        return Results.success(user);
    }

    @ApiOperation(value = "创建或更新User Table")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<List<User>> save(@PathVariable Long organizationId, @RequestBody List<User> users) {
        validObject(users);
        SecurityTokenHelper.validTokenIgnoreInsert(users);
        userService.saveData(users);
        return Results.success(users);
    }

    @ApiOperation(value = "删除User Table")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody List<User> users) {
        SecurityTokenHelper.validToken(users);
        userRepository.batchDeleteByPrimaryKey(users);
        return Results.success();
    }

    @ApiOperation(value = "Export User Data")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/export")
    @ExcelExport(UserDTO.class)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public ResponseEntity<List<UserDTO>> export(UserDTO userDTO,
                                                ExportParam exportParam,
                                                HttpServletResponse response,
                                                @PathVariable String organizationId) {
        return Results.success(userService.exportData(userDTO));
    }

}

