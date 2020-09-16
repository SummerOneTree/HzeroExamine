package org.hzero.todo.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.todo.domain.entity.User;
import org.hzero.todo.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

/**
 * @Date 2020/9/15 13:31
 * @Author Summer_OneTree
 */
@Component
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {
}
