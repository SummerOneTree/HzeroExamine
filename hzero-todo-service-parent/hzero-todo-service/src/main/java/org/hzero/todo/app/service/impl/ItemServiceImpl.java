package org.hzero.todo.app.service.impl;

import org.hzero.todo.app.service.ItemService;
import org.hzero.todo.domain.entity.Item;
import org.hzero.todo.domain.repository.ItemRepository;
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

    @Override
    public int insertItem(Item item) {
        return itemRepository.insert(item);
    }

    @Override
    public int updateItem(Item item) {
        return itemRepository.updateByPrimaryKey(item);
    }

    @Override
    public int deleteItem(int itemId) {
        return itemRepository.deleteByPrimaryKey(itemId);
    }

    @Override
    public List<Item> selectAllItem() {
        return itemRepository.selectAll();
    }
}
