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
import androidx.recyclerview.widget.RecyclerView;

import com.eazywrite.app.R;
import com.eazywrite.app.data.model.OutputBean;
import com.eazywrite.app.data.model.OutputViewModel;
import com.eazywrite.app.databinding.FragmentBillTwoBinding;
import com.eazywrite.app.ui.bill.adapter.RecycleViewAdapter;

import java.util.ArrayList;


public class FragmentTwo extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill_two, container,false);
        return mBinding.getRoot();
    }
    FragmentBillTwoBinding mBinding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mBinding.recycleViewTwo.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        RecycleViewAdapter adapter = new RecycleViewAdapter(viewModel.inputBean.getValue(), getContext());
        mBinding.recycleViewTwo.setAdapter(adapter);
    }

    OutputViewModel viewModel;
    private void initData() {
        viewModel = new ViewModelProvider(this).get(OutputViewModel.class);
        init();
        viewModel.inputBean.setValue(beans);
    }


    ArrayList<OutputBean> beans = new ArrayList<>();
    private void init() {
        beans.add(setResource("income_gongzi_g_con","工资"));
        beans.add(setResource("income_jianzhi_g_icon","兼职"));
        beans.add(setResource("pay_lijin_g_icon","礼金"));
        beans.add(setResource("income_qita_g_icon","其他"));
        beans.add(setResource("baoxiao_expend_shezhi_g_icon","设置"));
    }

    public OutputBean setResource(String id, String name){
        OutputBean bean = new OutputBean();
        bean.setImageId(getResources().getIdentifier(id,"drawable",getActivity().getPackageName()));
        bean.setName(name);
        return bean;
    }
}