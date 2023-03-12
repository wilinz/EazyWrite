package com.eazywrite.app.ui.bill.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.eazywrite.app.R;
import com.eazywrite.app.data.model.WeekBillBean;
import com.eazywrite.app.databinding.FragmentBillAddItemBinding;
import com.eazywrite.app.ui.bill.adapter.RecycleViewAdapter;
import com.eazywrite.app.ui.bill.adapter.ViewPager2Adapter;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AddItemFragment extends Fragment implements CountInterface,View.OnClickListener{

    public AddItemFragment(MainFragment mMainFragment) {
        this.mMainFragment = mMainFragment;
    }

    MainFragment mMainFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill_add_item,container,false);
        mBinding.save.setOnClickListener(this);
        mBinding.clear3.setOnClickListener(this);
        mBinding.datePicker.setOnClickListener(this);
        return  mBinding.getRoot();
    }
    private DataViewModel mDataViewModel;
    private ViewPager2Adapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        initDate();
        addNote();
        init();
        back();
        adapter = new ViewPager2Adapter(getActivity(), fragments);
        mBinding.viewPager2.setAdapter(adapter);

        new TabLayoutMediator(mBinding.tabLayout,mBinding.viewPager2,((tab, position) -> {

            if (position == 0) {
                tab.setText("支出");
            }else {
                tab.setText("输入");
            }
        })).attach();
    }

    private void initDate() {
        // 获取当前日期
        LocalDate currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
            // 将月日转换为指定格式的字符串
            String monthDay = currentDate.format(DateTimeFormatter.ofPattern("M月d日"));

            // 将年份转换为字符串
            String year = String.valueOf(currentDate.getYear());

            mDataViewModel.getDayMonth().setValue(monthDay);
            mDataViewModel.getYear().setValue(year);

            mBinding.datePicker.setText(monthDay);
        }


    }

    StringBuilder beiZhu = new StringBuilder();
    StringBuilder titleName = new StringBuilder();
    private void addNote() {
        mBinding.titleName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                titleName.replace(0, titleName.length(), charSequence.toString());
                mDataViewModel.getTitleName().setValue(titleName.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mBinding.note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                beiZhu.replace(0, beiZhu.length(), charSequence.toString());
                mDataViewModel.getNote().setValue(beiZhu.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
        FragmentOne fragmentOne = new FragmentOne(mMainFragment,this);
        FragmentTwo fragmentTwo = new FragmentTwo(mMainFragment,this);
        fragments.add(fragmentOne);
        fragments.add(fragmentTwo);
    }

    FragmentBillAddItemBinding mBinding;

    @Override
    public void getCount(String count) {
        mDataViewModel.getCount().setValue(count);
        mBinding.count.setText(count);
    }

    @Override
    public void getImage(int imageId) {
        mDataViewModel.getImageId().setValue(imageId);
        mBinding.imageId.setImageResource(imageId);
    }

    @Override
    public void getName(String name) {
        mDataViewModel.getCategory().setValue(name);
        mBinding.category.setText(name);
    }

    @Override
    public void getInOrOut(Boolean inOrOut) {
        mDataViewModel.getInOrOut().setValue(inOrOut);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear3:
                clear();
                break;
            case R.id.datePicker:
                dataPicker();
                break;
            case R.id.save:
                save();
                break;
        }
    }

    private void save() {
        OutputBean outputBean = new OutputBean();
        outputBean.setDate(new StringBuilder(mDataViewModel.getDayMonth().getValue()));
        outputBean.setBeiZhu(new StringBuilder(mDataViewModel.getNote().getValue()));
        outputBean.setMoneyCount(new StringBuilder(mDataViewModel.getCount().getValue()));
        outputBean.setName(mDataViewModel.getCategory().getValue());
        outputBean.setImageId(mDataViewModel.getImageId().getValue());
        outputBean.setCategory(mDataViewModel.getCategory().getValue());
        outputBean.setDayMonth(mDataViewModel.getDayMonth().getValue());
        outputBean.setYear(mDataViewModel.getYear().getValue());
        outputBean.setInOrOut(mDataViewModel.getInOrOut().getValue());
        List<OutputBean> list = new ArrayList<>();
        list.add(outputBean);
        WeekBillBean weekBillBean = new WeekBillBean();
        weekBillBean.setWeekDate(mDataViewModel.getDayMonth().getValue());
        List<WeekBillBean> weekBillBeanList = new ArrayList<>();
        weekBillBeanList.add(weekBillBean);
        mMainFragment.addData(weekBillBeanList);
        Log.d("HelloWorld", outputBean.toString());
        getActivity().finish();
    }


    public void clear() {
        mDataViewModel.clear();
        Bitmap emptyBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        mBinding.imageId.setImageBitmap(emptyBitmap);
        mBinding.titleName.setText("账目名称");
        mBinding.category.setText("账目类别");
        mBinding.note.setText("");
        mBinding.count.setText("0.00");
        mBinding.titleName.clearFocus();
        mBinding.note.clearFocus();
        initDate();
        fragments.clear();
        init();
        adapter = new ViewPager2Adapter(getActivity(), fragments);
        mBinding.viewPager2.setAdapter(adapter);
    }



    private void dataPicker() {
        MaterialDatePicker<Long> datePicker= MaterialDatePicker.Builder.datePicker()
                .setTheme(R.style.ThemeOverlay_App_DatePicker)
                        .setTitleText("日期选择").build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            String dateStr = DateFormat.getDateInstance().format(new Date(selection));
            String yearStr = dateStr.substring(0, 4) + "年";
            String monthDayStr = dateStr.substring(5);
            mBinding.datePicker.setText(monthDayStr);
            mDataViewModel.getDayMonth().setValue(monthDayStr);
            mDataViewModel.getYear().setValue(yearStr);
        });
        datePicker.addOnNegativeButtonClickListener(view -> {

        });

        datePicker.show(getActivity().getSupportFragmentManager(), "data");
    }









}