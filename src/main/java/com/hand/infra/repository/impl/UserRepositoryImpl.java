package com.hand.infra.repository.impl;

import com.hand.api.dto.request.UserDTO;
import org.apache.commons.collections.CollectionUtils;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Component;
import com.hand.domain.entity.User;
import com.hand.domain.repository.UserRepository;
import com.hand.infra.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * User Table(User)资源库
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-12-02 15:17:34
 */
@Component
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {
    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> selectList(User user) {
        return userMapper.selectList(user);
    }

    @Override
    public List<UserDTO> selectList(UserDTO user) {
        return userMapper.selectListExport(user);
    }

    @Override
    public User selectByPrimary(Long id) {
        User user = new User();
        user.setId(id);
        List<User> users = userMapper.selectList(user);
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

}

