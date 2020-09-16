package org.hzero.todo.infra.util;

/**
 * @Date 2020/8/25 17:32
 * @Author Summer_OneTree
 */
public class NumberAddOne {
    public static String numberAddOne(String testStr){
        // 根据不是数字的字符拆分字符串
        String[] strs = testStr.split("[^0-9]");
        // 去除最后一组数字
        String numStr = strs[strs.length - 1];
        // 如果最后一组没有数字(也就是不以数字结尾)，抛NumberFormatException异常
        if(numStr != null && numStr.length() > 0) {
            // 取出字符串的长度
            int n = numStr.length();
            //数字加1
            int num = Integer.parseInt(numStr) + 1;
            String added = String.valueOf(num);
            n = Math.min(n, added.length());
            // 拼接字符串
            return testStr.subSequence(0, testStr.length() - n) + added;
        } else {
            throw new NumberFormatException();
        }
    }
}
