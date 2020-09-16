package org.hzero.todo.domain.service;

import org.hzero.todo.domain.entity.Item;

import java.util.List;

/**
 * @Date 2020/8/18 16:27
 * @Author Summer_OneTree
 */
public interface ItemService {

    /**
     * 新增一条物料信息
     * @param item 物料实体
     */
    void insertItem(Item item);

    /**
     * 更新一条物料信息
     * @param item 物料实体
     */
    void updateItem(Item item);

    /**
     * 删除一条物料信息
     * @param itemId 物料Id
     */
    void deleteItem(long itemId);

    /**
     * 查询所有物料信息
     * @return 返回所有物料信息
     */
    List<Item> selectAllItem();
}