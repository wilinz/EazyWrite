package com.eazywrite.app.data.model;

import com.eazywrite.app.ui.bill.fragment.OutputBean;

import java.util.ArrayList;
import java.util.List;

public class WeekBillBean {
    private String weekDate;

    private List<OutputBean> weekBillBeanList;

    public String getWeekDate() {
        return weekDate;
    }

    public void setWeekDate(String weekDate) {
        this.weekDate = weekDate;
    }

    public List<OutputBean> getWeekBillBeanList() {
        return weekBillBeanList;
    }

    public void setWeekBillBeanList(List<OutputBean> weekBillBeanList) {
        this.weekBillBeanList = weekBillBeanList;
    }
}
