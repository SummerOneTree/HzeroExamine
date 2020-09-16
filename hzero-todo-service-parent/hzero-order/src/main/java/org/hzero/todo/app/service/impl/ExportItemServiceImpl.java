package org.hzero.todo.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.hzero.todo.domain.entity.ExcelData;
import org.hzero.todo.domain.entity.Item;
import org.hzero.todo.domain.repository.ItemRepository;
import org.hzero.todo.app.service.ExportItemDataService;
import org.hzero.todo.infra.util.PoiExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2020/8/27 9:37
 * @Author Summer_OneTree
 */
@Slf4j
@Service
public class ExportItemServiceImpl implements ExportItemDataService {
    public static final String SUFFIX = ".xlsx";

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void exportData(HttpServletResponse response, String fileName) {
        log.info("导出数据开始...");
        fileName = fileName + SUFFIX;
        List<Item> items = itemRepository.selectAll();
        List<String[]> list = new ArrayList<>();
        for (Item item : items) {
            String[] arrs = new String[items.size()];
            arrs[0] = String.valueOf(item.getItemId());
            arrs[1] = String.valueOf(item.getItemCode());
            arrs[2] = String.valueOf(item.getItemUom());
            arrs[3] = String.valueOf(item.getItemDescription());
            arrs[4] = String.valueOf(item.getStartActiveDate());
            arrs[5] = String.valueOf(item.getEndActiveDate());
            arrs[6] = String.valueOf(item.getEnabledFlag());

            list.add(arrs);
        }
        String[] head = {"物料ID", "物料编码", "物料单位", "物料描述", "物料生效时间", "物料失效时间", "物料是否启用"};

        ExcelData data = new ExcelData();
        data.setFileName(fileName);
        data.setHead(head);
        data.setData(list);
        data.setSheetName("hodr_item");

        try {
            PoiExcelUtil.exportExcel(response,data);
            log.info("导出数据结束。。。");
        } catch (Exception e) {
            log.info("数据导出失败。。。");
        }
    }
}
