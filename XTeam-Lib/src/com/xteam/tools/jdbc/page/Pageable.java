package com.xteam.tools.jdbc.page;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-5-5
 * Time: 15:20:35
 * 分页接口
 */
public interface Pageable {
    /**
     * 返回总页数
     */
    int getPageCount();

    /**
     * 返回当前页的记录条数
     */
    int getPageRowsCount();

    /**
     * 返回分页大小
     */
    int getPageSize();

    /**
     * 转到指定页
     */
    void gotoPage(int page);

    /**
     * 设置分页大小
     */
    void setPageSize(int pageSize);

    /**
     * 返回总记录行数
     */
    int getRowsCount();

    /**
     * 转到当前页的第一条记录
     *
     * @throws java.sql.SQLException 异常说明。
     */
    void pageFirst() throws java.sql.SQLException;

    /**
     * 转到当前页的最后一条记录
     *
     * @throws java.sql.SQLException 异常说明。
     */
    void pageLast() throws java.sql.SQLException;

    /**
     * 返回当前页号
     */
    int getCurPage();
}
