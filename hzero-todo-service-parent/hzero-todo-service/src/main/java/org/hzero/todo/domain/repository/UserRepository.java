package org.hzero.todo.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.todo.domain.entity.User;

/**
 * 资源库接口，提供数据资源的操作方法，简单的增删改查和redis的增删查改
 * 用户资源库
 *
 * @Date 2020/8/14 10:49
 * @Author Summer_OneTree
 */
public interface UserRepository extends BaseRepository<User> {

}
