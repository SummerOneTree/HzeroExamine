package org.hzero.todo.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.todo.domain.entity.Item;

import java.util.List;

/**
 * @Date 2020/8/18 16:13
 * @Author Summer_OneTree
 */
public interface ItemRepository extends BaseRepository<Item> {

    /**
     * 分页查询任务
     *
     * @param item        Item
     * @param pageRequest 分页参数
     * @return Page<Item>
     */
    Page<Item> pageItem(Item item, PageRequest pageRequest);

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
