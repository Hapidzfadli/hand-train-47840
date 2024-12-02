package com.hand.domain.repository;

import com.hand.api.dto.request.UserDTO;
import org.hzero.mybatis.base.BaseRepository;
import com.hand.domain.entity.User;

import java.util.List;

/**
 * User Table(User)资源库
 *
 * @author hapid.fadli@hand-global.com
 * @since 2024-12-02 15:17:34
 */
public interface UserRepository extends BaseRepository<User> {
    /**
     * 查询
     *
     * @param user 查询条件
     * @return 返回值
     */
    List<User> selectList(User user);

    List<UserDTO> selectList(UserDTO user);

    /**
     * 根据主键查询（可关联表）
     *
     * @param id 主键
     * @return 返回值
     */
    User selectByPrimary(Long id);
}
