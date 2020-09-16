package org.hzero.todo.app.service.impl;

import org.hzero.todo.domain.entity.Order;
import org.hzero.todo.domain.entity.SoHeader;
import org.hzero.todo.domain.entity.SoLine;
import org.hzero.todo.domain.repository.SoLineRepository;
import org.hzero.todo.app.service.SoHeaderService;
import org.hzero.todo.app.service.SoLineService;
import org.hzero.todo.infra.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Date 2020/9/3 16:21
 * @Author Summer_OneTree
 */
@Service
public class SoLineServiceImpl implements SoLineService {

    @Autowired
    private SoLineRepository soLineRepository;

    @Autowired
    private SoHeaderService soHeaderService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 更新订单行信息
     * @param order Order
     * @return 更新成功条数
     */
    @Override
    public int updateSoLine(Order order) {
        // 开始,拿到从Web传过来的数据，从中取出 orderNumber lineNumber
        String orderNumber = order.getOrderNumber();
        Integer lineNumber = order.getSoLine().getLineNumber();

        // 拿到编号为 orderNumber 的数据 并且拿出其Id
        SoHeader soHeaderByOrderNumber = soHeaderService.getSoHeaderByOrderNumber(orderNumber);
        Long soHeaderId = soHeaderByOrderNumber.getSoHeaderId();

        // 将两条数据放进soLine中
        SoLine soLine = new SoLine();
        soLine.setSoHeaderId(soHeaderId);
        soLine.setLineNumber(lineNumber);

        // 查询出唯一的那一条行信息
        SoLine soLineServiceSoLine = soLineRepository.selectOne(soLine);
        Long soLineId = soLineServiceSoLine.getSoLineId();
        Long objectVersionNumber = soLineServiceSoLine.getObjectVersionNumber();

        // 将数据放进实体中，进行更新数据
        soLine.setSoLineId(soLineId);
        soLine.setLineNumber(lineNumber);
        soLine.setItemId(order.getSoLine().getItemId());
        soLine.setOrderQuantity(order.getSoLine().getOrderQuantity());
        soLine.setUnitSellingPrice(order.getSoLine().getUnitSellingPrice());
        soLine.setObjectVersionNumber(objectVersionNumber);
        soLine.setOrderQuantityUom(order.getSoLine().getOrderQuantityUom());
        soLine.setDescription(order.getSoLine().getDescription());
        soLine.setAddition1(order.getSoLine().getAddition1());
        soLine.setAddition2(order.getSoLine().getAddition2());
        soLine.setAddition3(order.getSoLine().getAddition3());
        soLine.setAddition4(order.getSoLine().getAddition4());
        soLine.setAddition5(order.getSoLine().getAddition5());

        System.out.println(soLine);

        return soLineRepository.updateSoLine(soLine);
    }

    /**
     * 查询一条订单行信息
     * @param soLine SoLine
     * @return soLine
     */
    @Override
    public SoLine getSoLine(SoLine soLine) {
        return soLineRepository.selectOne(soLine);
    }

    /**
     * 插入订单行信息
     * @param order Order
     * @return 插入成功条数
     */
    @Override
    public int insertSoLine(Order order) {
        // 开始,拿到从Web传过来的数据，从中取出 orderNumber lineNumber
        String orderNumber = order.getOrderNumber();

        // 计算lineNumber 行号为当前订单中行号最大 + 1
        int lineNumber;
        lineNumber = soLineRepository.getMaxLineNumber(orderNumber) + 1;

        // 拿到编号为 orderNumber 的数据 并且拿出其Id
        SoHeader soHeaderByOrderNumber = soHeaderService.getSoHeaderByOrderNumber(orderNumber);
        Long soHeaderId = soHeaderByOrderNumber.getSoHeaderId();

        // 将两条数据放进soLine中
        SoLine soLine = new SoLine();
        soLine.setSoHeaderId(soHeaderId);
        soLine.setLineNumber(lineNumber);
        soLine.setItemId(order.getSoLine().getItemId());
        soLine.setOrderQuantity(order.getSoLine().getOrderQuantity());
        soLine.setUnitSellingPrice(order.getSoLine().getUnitSellingPrice());
        soLine.setOrderQuantityUom(order.getSoLine().getOrderQuantityUom());
        soLine.setDescription(order.getSoLine().getDescription());
        soLine.setAddition1(order.getSoLine().getAddition1());
        soLine.setAddition2(order.getSoLine().getAddition2());
        soLine.setAddition3(order.getSoLine().getAddition3());
        soLine.setAddition4(order.getSoLine().getAddition4());
        soLine.setAddition5(order.getSoLine().getAddition5());

        // 防止并发插入问题
        boolean needDel = false;
        String key = null;
        int rsg = 0;
        try {
            // 查询在数据库中此数据是否存在
            SoLine soLineIsExist = soLineRepository.selectOne(soLine);
            if (null != soLineIsExist) {
                throw new Exception("数据库中已存在此数据");
            }
            Integer lineNumberKey = soLine.getLineNumber();
            key = lineNumberKey.toString();

            // 如果存入Redis缓存中失败，说明数据存在
            if (!redisUtils.set(key,soLine)) {
                throw new Exception("缓存中已存在此数据");
            };
            needDel = true;
            // 存入数据库
            rsg = soLineRepository.insert(soLine);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (needDel) {
                redisUtils.del(key);
            }
        }
        return rsg;
    }
}
