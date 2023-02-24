package com.eazywrite.app.ui.bill.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.eazywrite.app.R;
import com.eazywrite.app.databinding.FragmentBillAddItemBinding;
import com.eazywrite.app.ui.bill.adapter.ViewPager2Adapter;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


public class AddItemFragment extends Fragment {

    public AddItemFragment() {

    }

    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill_add_item,container,false);

        return  mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        back();
        ViewPager2Adapter adapter = new ViewPager2Adapter(getActivity(), fragments);
        mBinding.viewPager2.setAdapter(adapter);


        new TabLayoutMediator(mBinding.tabLayout,mBinding.viewPager2,((tab, position) -> {

            if (position == 0) {
                tab.setText("收入");
            }else {
                tab.setText("支出");
            }
        })).attach();
    }

    private void back() {
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private ArrayList<Fragment> fragments = new ArrayList<>();

    private void init() {
        FragmentOne fragmentOne = new FragmentOne();
        FragmentTwo fragmentTwo = new FragmentTwo();
        fragments.add(fragmentOne);
        fragments.add(fragmentTwo);
    }

    FragmentBillAddItemBinding mBinding;
}