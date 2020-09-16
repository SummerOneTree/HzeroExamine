package org.hzero.todo.infra.mapper;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.common.BaseMapper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.todo.domain.entity.Item;
import org.hzero.todo.domain.entity.Task;

import java.util.List;

/**
 * @Date 2020/8/18 16:05
 * @Author Summer_OneTree
 */
public interface ItemMapper extends BaseMapper<Item> {

    /**
     * 得到id最大的物料的itemCode信息
     *
     * @return 返回itemCode
     */
    String getItemCode();

    /**
     * 条件查新
     *
     * @param item 查询条件
     * @return 返回结果集
     */
    List<Item> getItemBySelect(Item item);
}
