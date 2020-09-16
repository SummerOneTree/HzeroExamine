package org.hzero.todo.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.todo.app.service.ItemService;
import org.hzero.todo.config.SwaggerApiConfig;
import org.hzero.todo.domain.entity.Item;
import org.hzero.todo.domain.repository.ItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/**
 * @Date 2020/8/18 16:42
 * @Author Summer_OneTree
 */
@Api(tags = SwaggerApiConfig.ITEM)
@RestController
@RequestMapping("item")
public class ItemController extends BaseController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    public ItemController(ItemService itemService, ItemRepository itemRepository) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
    }

    /**
     * 分页查询
     * @param item 物料实体
     * @param pageRequest 分页参数
     * @return 返回分页数据
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页查询")
    public ResponseEntity<Page<Item>> list(Item item, PageRequest pageRequest) {
        return Results.success(itemRepository.pageItem(item, pageRequest));
    }

    /**
     * 插入物料信息
     * @param item 物料实体
     */
    @PostMapping("/insert")
    @ApiOperation(value = "插入物料信息")
    public void insert(Item item) {
        itemService.insertItem(item);
    }

    /**
     * 插入物料信息
     * @param item 物料实体
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新物料信息")
    public void update(Item item) {
        item.setItemId((long) 197);
        item.setItemCode("ITEM0017871252");
        item.setItemCode("米");
        item.setItemDescription("测试数据");
        item.setStartActiveDate(Date.valueOf("2020-08-01"));
        item.setEndActiveDate(Date.valueOf("2020-08-01"));
        item.setEnabledFlag(true);
        itemService.updateItem(item);
    }

    /**
     * 插入物料信息
     * @param itemId 物料Id
     */
    @PostMapping("/delete")
    @ApiOperation(value = "更新物料信息")
    public void delete(int itemId) {
        itemService.deleteItem(itemId);
    }
}
