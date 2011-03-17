package com.xteam.tools.jdbc.page;

import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: tom
 * Date: 2006-5-5
 * Time: 15:27:52
 * 分页实现类
 */

public class PageResultSet implements Pageable {

    protected java.sql.ResultSet rs = null;
    protected int rowsCount;
    protected int pageSize;
    protected int curPage;
    protected String command = "";

    public PageResultSet(ResultSet rs) throws java.sql.SQLException {
        if (rs == null) throw new SQLException("given ResultSet is NULL", "user");
        rs.last();
        rowsCount = rs.getRow();
        rs.beforeFirst();
        this.rs = rs;
    }

    public ResultSet getResultSet() {
        return rs;
    }

    /**
     * 返回总页数
     */
    public int getPageCount() {
        if (rowsCount == 0) return 0;
        if (pageSize == 0) return 1;
        double tmpD = (double) rowsCount / pageSize;
        int tmpI = (int) tmpD;
        if (tmpD > tmpI) tmpI++;
        return tmpI;
    }


    /**
     * 返回当前页的记录条数
     */
    public int getPageRowsCount() {
        if (pageSize == 0) return rowsCount;
        if (getRowsCount() == 0) return 0;
        if (curPage != getPageCount()) return pageSize;
        return rowsCount - (getPageCount() - 1) * pageSize;
    }

    /**
     * 返回分页大小
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 转到指定页
     */
    public void gotoPage(int page) {
        if (rs == null)
            return;
        if (page < 1)
            page = 1;
        if (page > getPageCount())
            page = getPageCount();
        int row = (page - 1) * pageSize + 1;
        try {
            rs.absolute(row);
            curPage = page;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置分页大小
     */
    public void setPageSize(int pageSize) {
        if (pageSize >= 0) {
            this.pageSize = pageSize;
            curPage = 1;
        }
    }

    /**
     * 返回总记录行数
     */
    public int getRowsCount() {
        return rowsCount;
    }


    /**
     * 转到当前页的第一条记录
     *
     * @throws java.sql.SQLException 异常说明。
     */
    public void pageFirst() throws java.sql.SQLException {
        int row = (curPage - 1) * pageSize + 1;
        rs.absolute(row);
    }


    /**
     * 转到当前页的最后一条记录
     *
     * @throws java.sql.SQLException 异常说明。
     */
    public void pageLast() throws java.sql.SQLException {
        int row = (curPage - 1) * pageSize + getPageRowsCount();
        rs.absolute(row);
    }


    /**
     * 返回当前页号
     */
    public int getCurPage() {
        return curPage;
    }
}
