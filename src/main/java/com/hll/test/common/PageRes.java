package com.hll.test.common;

import java.util.List;
import java.util.Map;

public class PageRes extends Response{
    
    public PageRes(int pageCount, List<Map<String, Object>> data) {
        Util.removeOBID(data);
        this.pageCount = pageCount;
        this.setData(data);
    }

    private int pageCount;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    
}
