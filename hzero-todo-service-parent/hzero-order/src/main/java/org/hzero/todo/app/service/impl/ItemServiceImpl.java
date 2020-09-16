package org.hzero.todo.app.service.impl;


import org.hzero.todo.domain.entity.Item;
import org.hzero.todo.domain.repository.ItemRepository;
import org.hzero.todo.app.service.ItemService;
import org.hzero.todo.infra.util.NumberAddOne;
import org.hzero.todo.infra.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date 2020/8/18 16:31
 * @Author Summer_OneTree
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 插入物料信息
     * @param item 物料实体
     */
    @Override
    public void insertItem(Item item) {
        // 得到itemId最大的物料的itemCode
        String itemCodeInDataBase = itemRepository.getItemCode();
        if (itemCodeInDataBase == null) {
            itemCodeInDataBase = "ITEM0000000000";
        }
        String itemCode = NumberAddOne.numberAddOne(itemCodeInDataBase);
        item.setItemCode(itemCode);

        // 防止高并发重复插入
        boolean needDel = false;
        String key = null;
        try {
            // 查询数据库中是否存在
            Item itemByCode = itemRepository.getItemByCode(item.getItemCode());
            if (null != itemByCode) {
                throw new Exception("数据已存在:第一步");
            }
            key = item.getItemCode();
            // 如果存入失败则抛异常
            if (!redisUtils.set(key, item)) {
                throw new Exception("数据已存在：第二步");
            }
            needDel = true;
            // 存入数据库
            itemRepository.insert(item);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (needDel) {
                redisUtils.del(key);
            }
        }
    }

    /**
     * 更新物料信息
     * @param item 物料实体
     */
    @Override
    public void updateItem(Item item) {
        itemRepository.updateByPrimaryKey(item);
    }

    /**
     * 根据itemId删除物料
     * @param itemId 物料Id
     */
    @Override
    public void deleteItem(long itemId) {
        itemRepository.deleteByPrimaryKey(itemId);
    }

    /**
     * 查询所有物料信息
     * @return List<Item>
     */
    @Override
    public List<Item> selectAllItem() {
        return itemRepository.selectAll();
    }

    /**
     * 根据itemCode查询单个物料信息
     * @param item Item
     * @return item
     */
    @Override
    public Item selectOne(Item item) {
        return itemRepository.selectOne(item);
    }
}
