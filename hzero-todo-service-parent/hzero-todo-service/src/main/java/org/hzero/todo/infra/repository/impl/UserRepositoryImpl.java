package org.hzero.todo.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.todo.domain.repository.UserRepository;
import org.hzero.todo.domain.entity.User;
import org.springframework.stereotype.Component;

/**
 * repository的实现，业务不能入侵到这里
 *
 * @Date 2020/8/14 10:50
 * @Author Summer_OneTree
 */
@Component
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {

}
