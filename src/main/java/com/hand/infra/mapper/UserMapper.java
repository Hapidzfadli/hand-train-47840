package com.hand.infra.mapper;

import com.hand.api.dto.request.UserDTO;
import io.choerodon.mybatis.common.BaseMapper;
import com.hand.domain.entity.User;

import java.util.List;

/**
 * User Table(User)应用服务
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-12-02 15:17:34
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 基础查询
     *
     * @param user 查询条件
     * @return 返回值
     */
    List<User> selectList(User user);
    List<UserDTO> selectListExport(UserDTO user);
}

