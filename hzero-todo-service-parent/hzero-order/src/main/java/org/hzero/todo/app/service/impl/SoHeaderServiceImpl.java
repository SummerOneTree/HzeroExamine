package org.hzero.todo.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.hzero.todo.domain.entity.SoHeader;
import org.hzero.todo.domain.repository.SoHeaderRepository;
import org.hzero.todo.app.service.SoHeaderService;
import org.hzero.todo.infra.util.RedisUtils;
import org.hzero.todo.infra.util.SerialNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date 2020/9/3 15:48
 * @Author Summer_OneTree
 */
@Service
@Slf4j
public class SoHeaderServiceImpl implements SoHeaderService {

    private static final String APPROVED = "APPROVED";
    private static final String CLOSED = "CLOSED";

    @Autowired
    private SoHeaderRepository soHeaderRepository;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 插入订单头信息
     * @param soHeader SoHeader
     * @return 插入成功条数
     */
    @Override
    public int insertSoHeader(SoHeader soHeader) {
        soHeader.setOrderNumber(SerialNumberUtil.getSerialNumber());
        // 防止高并发重复插入
        boolean needDel = false;
        String key = null;
        int rsg = 0;
        try{
            // 从soHeader中取出 orderNumber
            String orderNumber = soHeader.getOrderNumber();
            // 查询数据库中是否存在
            SoHeader soHeaderIsExit = new SoHeader();
            soHeaderIsExit.setOrderNumber(orderNumber);
            SoHeader soHeaderByOrderNumber = soHeaderRepository.selectOne(soHeaderIsExit);
            if (null != soHeaderByOrderNumber) {
                throw new Exception("数据库中已存在此数据");
            }
            key = soHeader.getOrderNumber();
            // 如果存入失败贼抛异常
            if (!redisUtils.set(key,soHeader)) {
                throw new Exception("缓存中已存在此数据");
            }
            needDel = true;
            // 存入数据库
            rsg = soHeaderRepository.insert(soHeader);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (needDel) {
                redisUtils.del(key);
            }
        }

        return rsg;
    }

    /**
     * 通过订单标号得到订单头信息
     * @param orderNumber 订单编号
     * @return soHeader
     */
    @Override
    public SoHeader getSoHeaderByOrderNumber(String orderNumber) {
        SoHeader soHeader = new SoHeader();
        soHeader.setOrderNumber(orderNumber);
        return soHeaderRepository.selectOne(soHeader);
    }

    /**
     * 通过主键更新订单头信息表
     * @param soHeader SoHeader
     * @return 更新成功条数
     */
    @Override
    public int updateSoHeader(SoHeader soHeader) {

        return soHeaderRepository.updateByPrimaryKey(soHeader);
    }

    /**
     * 销售订单自动关闭功能
     */
    @Scheduled(cron = "00 00 03 * * ?")
    public void autoClosed(){
        SoHeader soHeaderSelect = new SoHeader();
        soHeaderSelect.setOrderStatus(APPROVED);
        List<SoHeader> soHeaderList = soHeaderRepository.select(soHeaderSelect);
        int count = 0;
        for (SoHeader soHeader : soHeaderList) {
            soHeader.setOrderStatus(CLOSED);
            int rsg = soHeaderRepository.updateByPrimaryKey(soHeader);
            if (rsg == 1) {
                count++;
            }
        }
        log.info("成功更新了记录{}条。。。",count);
    }

}
