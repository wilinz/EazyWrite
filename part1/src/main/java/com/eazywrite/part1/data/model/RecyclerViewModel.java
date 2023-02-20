package com.eazywrite.part1.data.model;

import android.content.res.Resources;

import androidx.lifecycle.ViewModel;

import com.eazywrite.part1.data.bean.OutputBean;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewModel extends ViewModel {
    ArrayList<OutputBean> mList = new ArrayList<>();

    public void init() {
        OutputBean outputBean = new OutputBean();
    }
}
