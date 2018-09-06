package com.arena.maraton;

public class ItemObject {

    private String img1;
    private String title;
    private String desc;

    public ItemObject(String img1, String title, String desc) {
        this.img1 = img1;
        this.title = title;
        this.desc = desc;
    }

    public String getimg1() {
        return img1;
    }

    public String gettitle() {
        return title;
    }

    public String getdesc() {
        return desc;
    }
}
