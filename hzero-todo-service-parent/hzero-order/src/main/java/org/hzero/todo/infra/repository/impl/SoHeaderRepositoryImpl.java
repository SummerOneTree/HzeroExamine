package org.hzero.todo.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.todo.domain.entity.SoHeader;
import org.hzero.todo.domain.repository.SoHeaderRepository;
import org.hzero.todo.infra.mapper.SoHeaderMapper;
import org.springframework.stereotype.Component;

/**
 * @Date 2020/8/31 13:46
 * @Author Summer_OneTree
 */
@Component
public class SoHeaderRepositoryImpl extends BaseRepositoryImpl<SoHeader> implements SoHeaderRepository {
    private final SoHeaderMapper soHeaderMapper;

    public SoHeaderRepositoryImpl(SoHeaderMapper soHeaderMapper) {
        this.soHeaderMapper = soHeaderMapper;
    }

    @Override
    public int updateSoHeader(SoHeader soHeader) {
        return soHeaderMapper.updateSoHeader(soHeader);
    }
}
