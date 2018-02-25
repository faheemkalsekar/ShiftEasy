package com.gadgetmedia.shifteasy.mvp.api;

public class BusinessResponse {
    private String logo;

    private String name;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BusinessResponse [logo = " + logo + ", name = " + name + "]";
    }
}
