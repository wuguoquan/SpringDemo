package com.demo.domain;

import java.io.Serializable;

public class ImageInfo implements Serializable {

    private Integer id;
    private Integer total;
    private String url;

    public ImageInfo(Integer id, Integer total, String url) {
        super();
        this.id = id;
        this.total = total;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}