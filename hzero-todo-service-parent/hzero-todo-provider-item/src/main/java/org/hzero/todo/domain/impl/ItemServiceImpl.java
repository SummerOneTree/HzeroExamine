package org.hzero.todo.domain.impl;


import org.hzero.todo.domain.entity.Item;
import org.hzero.todo.domain.repository.ItemRepository;
import org.hzero.todo.domain.service.ItemService;
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
    public void insertItem(Item item) {
        itemRepository.insert(item);
    }

    @Override
    public void updateItem(Item item) {
        itemRepository.updateByPrimaryKey(item);
    }

    @Override
    public void deleteItem(long itemId) {
        itemRepository.deleteByPrimaryKey(itemId);
    }

    @Override
    public List<Item> selectAllItem() {
        return itemRepository.selectAll();
    }
}
