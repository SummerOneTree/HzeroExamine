package org.hzero.todo.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.todo.domain.entity.Item;
import org.hzero.todo.domain.repository.ItemRepository;
import org.hzero.todo.domain.service.ExportItemDataService;
import org.hzero.todo.domain.service.ItemService;
import org.hzero.todo.util.NumberAddOne;
import org.hzero.todo.util.RedisUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

/**
 * @Date 2020/8/18 16:42
 * @Author Summer_OneTree
 */
@RestController
@RequestMapping("/item")
public class ItemController extends BaseController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final RedisUtils redisUtils;
    private final ExportItemDataService exportItemDataService;

    public ItemController(ItemService itemService, ItemRepository itemRepository, RedisUtils redisUtils, ExportItemDataService exportItemDataService) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
        this.redisUtils = redisUtils;
        this.exportItemDataService = exportItemDataService;
    }

    /**
     * 分页查询
     * @param item 物料实体
     * @param pageRequest 分页参数
     * @return 返回分页数据
     */
    @GetMapping("/list")
    public ResponseEntity<Page<Item>> list(Item item, PageRequest pageRequest) {
        return Results.success(itemRepository.pageItem(item, pageRequest));
    }

    /**
     * 插入物料信息
     * @param item 物料实体
     */
    @RequestMapping("/insert")
    public void insertRedisTest(Item item) throws InterruptedException {

        item.setItemUom("米");
        item.setItemDescription("1");
        item.setStartActiveDate(Date.valueOf("2020-08-25"));
        item.setEndActiveDate(Date.valueOf("2020-08-25"));
        item.setEnabledFlag(false);

        // 得到itemId最大的物料的itemCode
        String itemCodeInDB = itemRepository.getItemCode();
        if (itemCodeInDB == null) {
            itemCodeInDB = "ITEM0000000000";
        }
        String itemCode = NumberAddOne.numberAddOne(itemCodeInDB);
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
            itemService.insertItem(item);
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
    @RequestMapping("/update")
    public void update(Item item) {
        item.setItemId((long) 199);
        item.setItemCode("ITEM0017871254");
        item.setItemCode("米");
        item.setItemDescription("测试数据");
        item.setStartActiveDate(Date.valueOf("2020-08-01"));
        item.setEndActiveDate(Date.valueOf("2020-08-01"));
        item.setEnabledFlag(true);
        itemService.updateItem(item);
    }

    /**
     * 删除物料信息
     * @param itemId 物料Id
     */
    @RequestMapping("/delete/{itemId}")
    public void delete(@PathVariable(name = "itemId") long itemId) {
        itemService.deleteItem(itemId);
    }

    /**
     * 导出物料信息
     * @param response HttpServletResponse
     * @param fileName 文件名
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response, String fileName){
        fileName = "ItemData";
        exportItemDataService.exportData(response, fileName);
    }
}
