package org.hzero.todo.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.todo.domain.entity.SoHeader;

/**
 * @Date 2020/8/31 13:44
 * @Author Summer_OneTree
 */
public interface SoHeaderRepository extends BaseRepository<SoHeader> {
    /**
     * 提交更新
     * @param soHeader soHeader
     * @return 成功与否
     */
    int updateSoHeader(SoHeader soHeader);
}
