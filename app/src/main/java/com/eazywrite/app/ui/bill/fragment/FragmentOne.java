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

    private void initRecycleView() {
        mBinding.recycleView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        RecycleViewAdapter adapter = new RecycleViewAdapter(viewModel.outputBean.getValue(),getContext());
        mBinding.recycleView.setAdapter(adapter);
    }
    OutputViewModel viewModel;
    private void initData() {
        viewModel = new ViewModelProvider(this).get(OutputViewModel.class);
        init();
        viewModel.outputBean.setValue(beans);
    }

    ArrayList<OutputBean> beans = new ArrayList<>();

    private void init(){
        beans.add(setResource("jiaju1_1","家具"));
        beans.add(setResource("bangong1_1","办公"));
        beans.add(setResource("geren1_1","个人"));
        beans.add(setResource("gouwu1_1","购物"));
        beans.add(setResource("jianshen1_1","健身"));
        beans.add(setResource("jiaotong1_1","交通"));
        beans.add(setResource("jiating1_1","家庭"));
        beans.add(setResource("other1_1","其他"));
        beans.add(setResource("shenghuo1_1","生活"));
        beans.add(setResource("shouru1_1","收入"));
        beans.add(setResource("xuexi1_1","学习"));
        beans.add(setResource("yiliao1_1","医疗"));
        beans.add(setResource("yule1_1","娱乐"));
        beans.add(setResource("zhufang","住房"));
        beans.add(setResource("zhangbei","长辈"));
        beans.add(setResource("yundong","运动"));
        beans.add(setResource("yule","娱乐"));
        beans.add(setResource("yiliao","医疗"));
        beans.add(setResource("yaojiu","药酒"));
        beans.add(setResource("xuexi","学习"));
        beans.add(setResource("weixiu","未休"));
        beans.add(setResource("tongxun","通讯"));
        beans.add(setResource("shuma","数码"));
        beans.add(setResource("shuiguo","水果"));
        beans.add(setResource("shucai","蔬菜"));
        beans.add(setResource("shejiao","社交"));
        beans.add(setResource("riyong","日用"));
        beans.add(setResource("qinyou","亲友"));
        beans.add(setResource("pay_qiche_g_icon","汽车"));
        beans.add(setResource("pay_meirong_g_icon","美容"));
        beans.add(setResource("pay_lvxing_g_icon","相机"));
        beans.add(setResource("pay_kuaidi_g_icon","快递"));
        beans.add(setResource("pay_jujia_g_icon","车驾"));
        beans.add(setResource("pay_juanzeng_g_icon","捐赠"));


    }

    public OutputBean setResource(String id, String name){
        OutputBean bean = new OutputBean();
        bean.setImageId(getResources().getIdentifier(id,"drawable",getActivity().getPackageName()));
        bean.setName(name);
        return bean;
    }
}