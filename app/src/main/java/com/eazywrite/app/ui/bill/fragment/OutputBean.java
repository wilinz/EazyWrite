package com.eazywrite.app.ui.bill.fragment;

public class OutputBean {
    private int imageId;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public StringBuilder getBeiZhu() {
        return beiZhu;
    }

    public void setBeiZhu(StringBuilder beiZhu) {
        this.beiZhu = beiZhu;
    }

    public StringBuilder getDate() {
        return date;
    }

    public void setDate(StringBuilder date) {
        this.date = date;
    }

    public StringBuilder getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(StringBuilder moneyCount) {
        this.moneyCount = moneyCount;
    }

    private StringBuilder beiZhu;

    private StringBuilder date;

    private StringBuilder moneyCount;

}
