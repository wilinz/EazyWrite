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

    /**
     * 手动入录的信息都放在这里了
     */
    private MutableLiveData<StringBuilder> moneyCount = new MutableLiveData<>();
    private MutableLiveData<StringBuilder> beiZhu = new MutableLiveData<>();
    private MutableLiveData<StringBuilder> date = new MutableLiveData<>();

    public LiveData<StringBuilder> getDate() {
        return date;
    }

    public void setDate(StringBuilder date) {
        this.date.setValue(date);
    }

    public LiveData<StringBuilder> getBeiZhu() {
        return beiZhu;
    }

    public void setBeiZhu(StringBuilder beiZhu) {
        this.beiZhu.setValue(beiZhu);
    }

    public LiveData<StringBuilder> getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(StringBuilder moneyCount) {
        this.moneyCount.setValue(moneyCount);
    }


}
