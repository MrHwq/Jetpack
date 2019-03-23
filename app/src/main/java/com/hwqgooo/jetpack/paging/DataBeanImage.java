package com.hwqgooo.jetpack.paging;

public class DataBeanImage extends DataBean {
    public int id;
    public String name;

    public DataBeanImage(int id, String name) {
        super(id, name);
        this.id = id;
        this.name = name;
    }
}
