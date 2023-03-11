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
import com.eazywrite.app.databinding.FragmentBillTwoBinding;
import com.eazywrite.app.ui.bill.adapter.RecycleViewAdapter;

import java.util.ArrayList;


public class FragmentTwo extends Fragment {
    MainFragment mMainFragment;
    AddItemFragment mAddItemFragment;

    public FragmentTwo(MainFragment mainFragment, AddItemFragment addItemFragment) {
        this.mMainFragment = mainFragment;
        this.mAddItemFragment = addItemFragment;
    }

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
    RecycleViewAdapter adapter;
    private void initRecyclerView() {
        GridLayoutManager layoutManager =  new GridLayoutManager(getActivity(),4);
        mBinding.recycleViewTwo.setLayoutManager(layoutManager);
        adapter = new RecycleViewAdapter(viewModel.inputBean.getValue(), getContext(),
                viewModel.inputBeanColored.getValue(), layoutManager,getActivity().getSupportFragmentManager(),
                mAddItemFragment, this);
        mBinding.recycleViewTwo.setAdapter(adapter);
    }

    OutputViewModel viewModel;
    private void initData() {
        viewModel = new ViewModelProvider(this).get(OutputViewModel.class);
        init();
        viewModel.inputBean.setValue(beans);
        viewModel.inputBeanColored.setValue(beansColored);
    }


    ArrayList<OutputBean> beans = new ArrayList<>();
    ArrayList<OutputBean> beansColored = new ArrayList<>();
    private void init() {
        beans.add(setResource("gong_zi","工资"));
        beans.add(setResource("jian_zhi","兼职"));
        beans.add(setResource("li_cai","理财"));
        beans.add(setResource("li_jin","礼金"));
        beans.add(setResource("zhuan_zhang","转账"));

        beansColored.add(setResource("gong_zi1","工资"));
        beansColored.add(setResource("jian_zhi1","兼职"));
        beansColored.add(setResource("li_cai1","理财"));
        beansColored.add(setResource("li_jin1","礼金"));
        beansColored.add(setResource("zhuan_zhang1","转账"));


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