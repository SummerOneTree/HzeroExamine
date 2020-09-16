package org.hzero.todo.domain.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @Date 2020/8/27 9:37
 * @Author Summer_OneTree
 */
public interface ExportItemDataService {

    /**
     * 导出数据为excel
     * @param response HttpServletResponse
     * @param fileName 文件名
     */
    void exportData(HttpServletResponse response, String fileName);
}
