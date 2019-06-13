package com.example.luowenliang.idouban.moviedetail.entity;

public class MovieResourceInfo {
    private String resourceIcon;
    private String resourceName;
    private boolean needPay;
    private String resourceUrl;

    public MovieResourceInfo(String resourceIcon, String resourceName, boolean needPay, String resourceUrl) {
        this.resourceIcon = resourceIcon;
        this.resourceName = resourceName;
        this.needPay = needPay;
        this.resourceUrl = resourceUrl;
    }

    public String getResourceIcon() {
        return resourceIcon;
    }

    public void setResourceIcon(String resourceIcon) {
        this.resourceIcon = resourceIcon;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public boolean isNeedPay() {
        return needPay;
    }

    public void setNeedPay(boolean needPay) {
        this.needPay = needPay;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
}
