package com.arena.maraton;

public class ItemObject {

    private String img1;
    private String title;
    private String desc;
    private String id;
    private long needFund;
    private long sumFund;
    private int needTime;

    public ItemObject(String id, String img1, String title, String desc, long needFund, long sumFund, int needTime) {
        this.img1 = img1;
        this.title = title;
        this.desc = desc;
        this.id = id;
        this.needFund = needFund;
        this.sumFund = sumFund;
        this.needTime = needTime;
    }

    public String getimg1() {
        return img1;
    }

    public String getid() {
        return id;
    }

    public String gettitle() {
        return title;
    }

    public String getdesc() {
        return desc;
    }

    public long getneedFund() {
        return needFund;
    }

    public long getsumFund() {
        return sumFund;
    }

    public long getNeedTime() {
        return needTime;
    }
}
