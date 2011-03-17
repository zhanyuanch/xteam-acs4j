package com.xteam.tools.jdbc.page;


/**
 * Created by IntelliJ IDEA.
 * User: tom
 * Date: 2006-10-21
 * Time: 12:21:47
 * 保存分页过程中的数据
 */
public class PageBean {
	
	public static final int PAGESIZE	= 16;

    private int pageSize    = PAGESIZE;
    private int pageCount   = 1;
    private int rowCount    = 1;
    private int curtPage    = 1;

    private int nextPage    = 2;
    private int prepPage    = 1;
    
    
    public String getHtml(String action,String parmStr) {
        StringBuffer html   = new StringBuffer("");
        html.append("当前<b>"+getCurtPage()+"/"+getPageCount()+"</b>页&nbsp;&nbsp;\n" +
                "共<b>"+(getRowCount()-1)+"</b>条&nbsp;&nbsp;\n" +
                "<a href=\""+action+"?pageSize="+getPageSize()+"&curtPage=1&"+parmStr+"\">首页</a>\n" +
                "<a href=\""+action+"?pageSize="+getPageSize()+"&curtPage="+getPrepPage()+"&"+parmStr+"\">上页</a>\n" +
                "<a href=\""+action+"?pageSize="+getPageSize()+"&curtPage="+getNextPage()+"&"+parmStr+"\">下页</a>\n" +
                "<a href=\""+action+"?pageSize="+getPageSize()+"&curtPage="+getPageCount()+"&"+parmStr+"\">尾页</a>&nbsp;&nbsp;\n" +
                "<input type=\"text\" name=\"pageSize\" value=\""+getPageSize()+"\" size=\"3\">条/页&nbsp;&nbsp;\n" +
                "到<input type=\"text\" name=\"curtPage\" value=\""+getCurtPage()+"\" size=\"3\">\n" +
                "页<input type=\"button\" value=\"GO\" onClick=\"location.href='"+action+"?pageSize='+document.all['pageSize'].value+'&curtPage='+document.all['curtPage'].value+'&"+parmStr+"';\">");
        return html.toString();
    }
    

    public String getSimpHtml(String action,String parmStr) {
        StringBuffer html   = new StringBuffer("");
        html.append("当前<b>"+getCurtPage()+"/"+getPageCount()+"</b>页&nbsp;&nbsp;\n" +
                "共<b>"+(getRowCount()-1)+"</b>条&nbsp;&nbsp;\n" +
                "<a href=\""+action+"?pageSize="+getPageSize()+"&curtPage=1&"+parmStr+"\">首页</a>\n" +
                "<a href=\""+action+"?pageSize="+getPageSize()+"&curtPage="+getPrepPage()+"&"+parmStr+"\">上页</a>\n" +
                "<a href=\""+action+"?pageSize="+getPageSize()+"&curtPage="+getNextPage()+"&"+parmStr+"\">下页</a>\n" +
                "<a href=\""+action+"?pageSize="+getPageSize()+"&curtPage="+getPageCount()+"&"+parmStr+"\">尾页</a>&nbsp;&nbsp;\n " +getPageSize()+ " 条/页&nbsp;&nbsp;");
        return html.toString();
    }
    
    public String getHtml(String action){
        StringBuffer html   = new StringBuffer("");
        html.append("当前<b>"+getCurtPage()+"/"+getPageCount()+"</b>页&nbsp;&nbsp;\n" +
                "共<b>"+(getRowCount()-1)+"</b>条&nbsp;&nbsp;\n" +
                "<a href=\""+action+"?pageSize="+getPageSize()+"&curtPage=1\">首页</a>\n" +
                "<a href=\""+action+"?pageSize="+getPageSize()+"&curtPage="+getPrepPage()+"\">上页</a>\n" +
                "<a href=\""+action+"?pageSize="+getPageSize()+"&curtPage="+getNextPage()+"\">下页</a>\n" +
                "<a href=\""+action+"?pageSize="+getPageSize()+"&curtPage="+getPageCount()+"\">尾页</a>&nbsp;&nbsp;\n" +
                "<input type=\"text\" name=\"pageSize\" value=\""+getPageSize()+"\" size=\"3\">条/页&nbsp;&nbsp;\n" +
                "到<input type=\"text\" name=\"curtPage\" value=\""+getCurtPage()+"\" size=\"3\">\n" +
                "页<input type=\"button\" value=\"GO\" onClick=\"location.href='"+action+"?pageSize='+document.all['pageSize'].value+'&curtPage='+document.all['curtPage'].value;\">");
        return html.toString();
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
    	if(pageSize<1) pageSize	= PAGESIZE;
        this.pageSize = pageSize;
    }

    public int getCurtPage() {
        return curtPage;
    }

    public void setCurtPage(int curtPage) {
    	if(curtPage<1) curtPage	= 1;
        this.curtPage = curtPage;
    }


    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public void setPrepPage(int prepPage) {
        this.prepPage = prepPage;
    }

    public int getNextPage() {
        nextPage    = (curtPage<pageCount)?curtPage+1:1;
        return nextPage;
    }

    public int getPrepPage() {
        prepPage    = (curtPage>1)?curtPage-1:pageCount;
        return prepPage;
    }
}
