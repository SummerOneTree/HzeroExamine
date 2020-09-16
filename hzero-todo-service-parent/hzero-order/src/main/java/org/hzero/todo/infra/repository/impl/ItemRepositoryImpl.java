package org.hzero.todo.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.todo.domain.entity.Item;
import org.hzero.todo.domain.repository.ItemRepository;
import org.hzero.todo.infra.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date 2020/8/18 16:22
 * @Author Summer_OneTree
 */
@Component
public class ItemRepositoryImpl extends BaseRepositoryImpl<Item> implements ItemRepository {

    private final ItemMapper itemMapper;

    public ItemRepositoryImpl(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Override
    public Page<Item> pageItem(Item item, PageRequest pageRequest) {
        return PageHelper.doPage(pageRequest, itemMapper::selectAll);
    }

    @Override
    public String getItemCode() {
        return itemMapper.getItemCode();
    }

    @Override
    public List<Item> getItemBySelect(Item item) {
        return itemMapper.getItemBySelect(item);
    }

    @Override
    public Item getItemByCode(String itemCode) {
        return itemMapper.getItemByCode(itemCode);
    }
}
