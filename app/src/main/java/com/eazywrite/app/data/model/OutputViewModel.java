package com.eazywrite.app.data.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class OutputViewModel extends AndroidViewModel {

    public MutableLiveData<ArrayList<OutputBean>> beans = new MutableLiveData<>();

    public OutputViewModel(@NonNull Application application) {
        super(application);
    }



}
