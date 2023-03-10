package com.eazywrite.app.ui.bill.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.eazywrite.app.R;
import com.eazywrite.app.databinding.FragmentBillOneBinding;
import com.eazywrite.app.ui.bill.AddBillContentActivity;
import com.eazywrite.app.ui.bill.adapter.RecycleViewAdapter;

import java.util.ArrayList;

public class FragmentOne extends Fragment {
    AddItemFragment mAddItemFragment;
    MainFragment mMainFragment;
    public FragmentOne(MainFragment mainFragment, AddItemFragment addItemFragment) {
        this.mMainFragment = mainFragment;
        this.mAddItemFragment = addItemFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill_one,container,false);

        return  mBinding.getRoot();
    }

    FragmentBillOneBinding mBinding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initRecycleView();
    }

    RecycleViewAdapter adapter;
    private void initRecycleView() {
        GridLayoutManager layoutManager =  new GridLayoutManager(getActivity(),4);
        mBinding.recycleView.setLayoutManager(layoutManager);
        adapter = new RecycleViewAdapter(viewModel.outputBean.getValue(),getContext(),
                viewModel.outputBeanColored.getValue(),layoutManager,getActivity().getSupportFragmentManager()
        ,mAddItemFragment,this);
        mBinding.recycleView.setAdapter(adapter);
    }
    OutputViewModel viewModel;
    private void initData() {
        viewModel = new ViewModelProvider(this).get(OutputViewModel.class);
        init();
        viewModel.outputBean.setValue(beans);
        viewModel.outputBeanColored.setValue(beansColored);
    }

    ArrayList<OutputBean> beans = new ArrayList<>();
    ArrayList<OutputBean> beansColored = new ArrayList<>();

    private void init(){
        beans.add(setResource("baoxian1","??????"));
        beans.add(setResource("canying1","??????"));
        beans.add(setResource("fushi1","??????"));
        beans.add(setResource("fuwu1","??????"));
        beans.add(setResource("gongyi1","??????"));
        beans.add(setResource("gouwu1","??????"));
        beans.add(setResource("jiaotong1","??????"));
        beans.add(setResource("jiaoyu","??????"));
        beans.add(setResource("lvxing1","??????"));
        beans.add(setResource("yiliao1","??????"));
        beans.add(setResource("yule1","??????"));
        beans.add(setResource("yundong1","??????"));
        beans.add(setResource("zhuanzhang","??????"));


        beansColored.add(setResource("baoxian","??????"));
        beansColored.add(setResource("canying","??????"));
        beansColored.add(setResource("fushi","??????"));
        beansColored.add(setResource("fuwu","??????"));
        beansColored.add(setResource("gongyi","??????"));
        beansColored.add(setResource("gouwu","??????"));
        beansColored.add(setResource("jiaotong","??????"));
        beansColored.add(setResource("jiaoyu1","??????"));
        beansColored.add(setResource("lvxing","??????"));
        beansColored.add(setResource("yiliao","??????"));
        beansColored.add(setResource("yule","??????"));
        beansColored.add(setResource("yundong","??????"));
        beansColored.add(setResource("zhuanzhang1","??????"));


    }





    

    @Override
    public void onPause() {
        super.onPause();
        adapter.resume();
    }

    public OutputBean setResource(String id, String name){
        OutputBean bean = new OutputBean();
        bean.setImageId(getResources().getIdentifier(id,"drawable",getActivity().getPackageName()));
        bean.setName(name);
        return bean;
    }


}