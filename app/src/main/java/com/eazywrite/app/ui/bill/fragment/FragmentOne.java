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
import com.eazywrite.app.data.model.OutputBean;
import com.eazywrite.app.data.model.OutputViewModel;
import com.eazywrite.app.databinding.FragmentBillOneBinding;
import com.eazywrite.app.ui.bill.adapter.RecycleViewAdapter;

import java.util.ArrayList;

public class FragmentOne extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                viewModel.outputBeanColored.getValue(),layoutManager);
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
        beans.add(setResource("jiaju","家具"));
        beans.add(setResource("bangong","办公"));
        beans.add(setResource("geren","个人"));
        beans.add(setResource("gouwu","购物"));
        beans.add(setResource("jianshen","健身"));
        beans.add(setResource("jiaotong","交通"));
        beans.add(setResource("jiating","家庭"));
        beans.add(setResource("qita","其他"));
        beans.add(setResource("shenghuo","生活"));
        beans.add(setResource("shouru","收入"));
        beans.add(setResource("xuexi","学习"));
        beans.add(setResource("yiliao","医疗"));
        beans.add(setResource("yule","娱乐"));
        beans.add(setResource("zhufang","住房"));
        beans.add(setResource("zhangbei","长辈"));
        beans.add(setResource("yundong","运动"));
        beans.add(setResource("xuexi","学习"));
        beans.add(setResource("weixiu","未休"));
        beans.add(setResource("tongxun","通讯"));
        beans.add(setResource("shuma","数码"));
        beans.add(setResource("shuiguo","水果"));
        beans.add(setResource("shucai","蔬菜"));
        beans.add(setResource("shejiao","社交"));
        beans.add(setResource("riyong","日用"));
        beans.add(setResource("qinyou","亲友"));
        beans.add(setResource("qiche","汽车"));
        beans.add(setResource("meirong","美容"));
        beans.add(setResource("kuaidi","快递"));

        beans.add(setResource("juanzeng","捐赠"));

        beansColored.add(setResource("jiaju1","家具"));
        beansColored.add(setResource("bangong1","办公"));
        beansColored.add(setResource("geren1","个人"));
        beansColored.add(setResource("gouwu1","购物"));
        beansColored.add(setResource("jianshen1","健身"));
        beansColored.add(setResource("jiaotong1","交通"));
        beansColored.add(setResource("jiating1","家庭"));
        beansColored.add(setResource("qita1","其他"));
        beansColored.add(setResource("shenghuo1","生活"));
        beansColored.add(setResource("shouru1","收入"));
        beansColored.add(setResource("xuexi1","学习"));
        beansColored.add(setResource("yiliao1","医疗"));
        beansColored.add(setResource("yule1","娱乐"));
        beansColored.add(setResource("zhufang1","住房"));
        beansColored.add(setResource("zhangbei1","长辈"));
        beansColored.add(setResource("yundong1","运动"));
        beansColored.add(setResource("xuexi1","学习"));
        beansColored.add(setResource("weixiu1","未休"));
        beansColored.add(setResource("tongxun1","通讯"));
        beansColored.add(setResource("shuma1","数码"));
        beansColored.add(setResource("shuiguo1","水果"));
        beansColored.add(setResource("shucai1","蔬菜"));
        beansColored.add(setResource("shejiao1","社交"));
        beansColored.add(setResource("riyong1","日用"));
        beansColored.add(setResource("qinyou1","亲友"));
        beansColored.add(setResource("qiche1","汽车"));
        beansColored.add(setResource("meirong1","美容"));
        beansColored.add(setResource("kuaidi1","快递"));
        beansColored.add(setResource("juanzeng1","捐赠"));

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