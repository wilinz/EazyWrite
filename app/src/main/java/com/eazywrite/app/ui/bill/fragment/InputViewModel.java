package com.eazywrite.app.ui.bill.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class InputViewModel extends AndroidViewModel {
    public InputViewModel(@NonNull Application application) {
        super(application);
    }

    private LiveData<StringBuilder> moneyCount = new MutableLiveData<>();
    private LiveData<StringBuilder> beiZhu = new MutableLiveData<>();

    public LiveData<StringBuilder> getBeiZhu() {
        return beiZhu;
    }

    public void setBeiZhu(LiveData<StringBuilder> beiZhu) {
        this.beiZhu = beiZhu;
    }

    public LiveData<StringBuilder> getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(LiveData<StringBuilder> moneyCount) {
        this.moneyCount = moneyCount;
    }


}
