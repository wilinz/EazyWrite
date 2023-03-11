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
        beans.add(setResource("service","服务"));
        beans.add(setResource("traver","旅游"));
        beans.add(setResource("transport","转账"));
        beans.add(setResource("sport","运动"));
        beans.add(setResource("shopping","购物"));
        beans.add(setResource("play","娱乐"));
        beans.add(setResource("medical","医疗"));
        beans.add(setResource("gong_yi","公益"));
        beans.add(setResource("education","教育"));
        beans.add(setResource("eat","饮食"));
        beans.add(setResource("cloth","服装"));
        beans.add(setResource("bus","交通"));
        beans.add(setResource("bao_xian","保险"));


        beansColored.add(setResource("service1","服务"));
        beansColored.add(setResource("traver1","旅游"));
        beansColored.add(setResource("transport1","转账"));
        beansColored.add(setResource("sport1","运动"));
        beansColored.add(setResource("shopping1","购物"));
        beansColored.add(setResource("play1","娱乐"));
        beansColored.add(setResource("medical1","医疗"));
        beansColored.add(setResource("gong_yi1","公益"));
        beansColored.add(setResource("education1","教育"));
        beansColored.add(setResource("eat1","饮食"));
        beansColored.add(setResource("cloth1","服装"));
        beansColored.add(setResource("bus1","交通"));
        beansColored.add(setResource("bao_xian1","保险"));


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