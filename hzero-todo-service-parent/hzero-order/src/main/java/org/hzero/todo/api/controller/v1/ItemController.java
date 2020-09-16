package org.hzero.todo.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.todo.domain.entity.Item;
import org.hzero.todo.domain.repository.ItemRepository;
import org.hzero.todo.app.service.ExportItemDataService;
import org.hzero.todo.app.service.ItemService;
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
    private final ExportItemDataService exportItemDataService;

    public ItemController(ItemService itemService, ItemRepository itemRepository, ExportItemDataService exportItemDataService) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
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
    public void insertRedisTest(Item item) {

        item.setItemUom("EA");
        item.setItemDescription("六角螺母");
        item.setSaleableFlag(false);
        item.setStartActiveDate(Date.valueOf("2020-09-4"));
        item.setEndActiveDate(Date.valueOf("2020-09-5"));
        item.setEnabledFlag(true);

        itemService.insertItem(item);
    }
    /**
     * 更新物料信息
     * @param item 物料实体
     */
    @RequestMapping("/update")
    public void update(Item item) {
        item.setItemCode("ITEM0000001837");
        Item selectOne = itemService.selectOne(item);

        item.setItemId(selectOne.getItemId());
        item.setObjectVersionNumber(selectOne.getObjectVersionNumber());
        item.setItemUom("EA");
        item.setItemDescription("硅胶圈（φ88）");
        item.setStartActiveDate(Date.valueOf("2020-09-04"));
        item.setEndActiveDate(Date.valueOf("2020-10-01"));
        item.setSaleableFlag(true);
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
