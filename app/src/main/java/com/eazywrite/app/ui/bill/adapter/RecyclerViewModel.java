package com.eazywrite.app.ui.bill.adapter;

import androidx.lifecycle.ViewModel;

import com.eazywrite.app.data.model.OutputBean;

import java.util.ArrayList;

public class RecyclerViewModel extends ViewModel {
    ArrayList<OutputBean> mList = new ArrayList<>();

    public void init() {
        OutputBean outputBean = new OutputBean();
    }
}
