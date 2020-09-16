package org.hzero.todo.infra.util;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 流水号生成
 * @Date 2020/9/4 16:03
 * @Author Summer_OneTree
 */
public class SerialNumberUtil {

    private static final String PREFIX = "SO";

    /**
     * 得到订单编号：SO + yyMMdd + 6位流水号
     * @return SerialNumber
     */
    public static String getSerialNumber(){
        return PREFIX + getBody() + getSuffix();
    }

    /**
     * 拿到订单编号body
     * @return body
     */
    private static String getBody() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        return sdf.format(date);
    }

    /**
     * 拿到订单编号后缀
     * @return suffix
     */
    private static int getSuffix() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }
}
