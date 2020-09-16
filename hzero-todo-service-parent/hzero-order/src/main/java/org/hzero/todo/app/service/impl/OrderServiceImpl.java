package org.hzero.todo.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.hzero.todo.domain.entity.SoHeader;
import org.hzero.todo.domain.entity.SoLine;
import org.hzero.todo.domain.repository.SoHeaderRepository;
import org.hzero.todo.domain.repository.SoLineRepository;
import org.hzero.todo.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date 2020/9/4 16:31
 * @Author Summer_OneTree
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SoHeaderRepository soHeaderRepository;

    @Autowired
    private SoLineRepository soLineRepository;

    /**
     * 整单删除订单
     * @param orderNumber 订单编号
     */
    @Override
    public int deleteOrder(String orderNumber) {
        // 通过orderNumber查询出soHeaderId
        SoHeader soHeader = new SoHeader();
        soHeader.setOrderNumber(orderNumber);
        SoHeader selectOne = soHeaderRepository.selectOne(soHeader);
        if (selectOne == null) {
            return 0;
        }
        Long soHeaderId = selectOne.getSoHeaderId();

        SoLine soLine = new SoLine();
        soLine.setSoHeaderId(soHeaderId);
        List<SoLine> soLines = soLineRepository.select(soLine);

        int soLineRsg = soLineRepository.batchDelete(soLines);

        int soHeaderRsg = soHeaderRepository.delete(selectOne);

        log.info("删除表hodr_so_header中{}条数据，删除表hodr_so_Line中{}条数据", soHeaderRsg, soLineRsg);
        return soLineRsg + soHeaderRsg;
    }
}
