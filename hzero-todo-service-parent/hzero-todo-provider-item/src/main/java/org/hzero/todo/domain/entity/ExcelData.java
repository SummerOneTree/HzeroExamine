package org.hzero.todo.domain.entity;

import java.util.List;

/**
 * @Date 2020/8/27 9:29
 * @Author Summer_OneTree
 */
public class ExcelData {
    /**
     * 文件名
     */
    private String fileName;

    /**
     * 表头数据
     */
    private String[] head;

    /**
     * 数据
     */
    private List<String[]> data;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getHead() {
        return head;
    }

    public void setHead(String[] head) {
        this.head = head;
    }

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }
}
