package com.eazywrite.app.ui.bill.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eazywrite.app.R;
import com.eazywrite.app.data.model.BillBean;
import com.eazywrite.app.data.model.WeekBillBean;
import com.eazywrite.app.databinding.DialogFragmentBinding;
import com.eazywrite.app.ui.bill.AddBillContentActivity;
import com.eazywrite.app.ui.bill.adapter.ItemRecyclerViewAdapter;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.litepal.LitePal;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class MyDialogFragment extends DialogFragment implements View.OnClickListener {
    private AddBillContentActivity mActivity;
    private FragmentManager mFragmentManager;
    private MainFragment mMainFragment;
    private OutputBean mOutputBean;

    private SharedPreferences pref;

    private String account;

    private List<WeekBillBean> weekBillBeanList;

    public MyDialogFragment(AddBillContentActivity activity, FragmentManager fragmentManager,
                            MainFragment mainFragment, OutputBean bean) {
        mActivity = activity;
        this.mMainFragment = mainFragment;
        mFragmentManager = fragmentManager;
        mOutputBean = bean;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        getDialog().getWindow().setWindowAnimations(R.style.dialogAnim);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(InputViewModel.class);

        return mBinding.getRoot();
    }
    InputViewModel mViewModel;

    DialogFragmentBinding mBinding;

    StringBuilder mBuilder = new StringBuilder("0");
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pref = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        account = pref.getString("account","");
        mBinding.money.setText(mBuilder);
        setClickListener();
    }

    private void setClickListener() {
        mBinding.zero.setOnClickListener(this);
        mBinding.one.setOnClickListener(this);
        mBinding.two.setOnClickListener(this);
        mBinding.three.setOnClickListener(this);
        mBinding.four.setOnClickListener(this);
        mBinding.five.setOnClickListener(this);
        mBinding.six.setOnClickListener(this);
        mBinding.seven.setOnClickListener(this);
        mBinding.eight.setOnClickListener(this);
        mBinding.nine.setOnClickListener(this);
        mBinding.finish.setOnClickListener(this);
        mBinding.dot.setOnClickListener(this);
        mBinding.delete.setOnClickListener(this);
        mBinding.date.setOnClickListener(this);
        mBinding.add.setOnClickListener(this);
        mBinding.sub.setOnClickListener(this);
        mBinding.beiZhu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                beiZhu.replace(0, beiZhu.length(), charSequence.toString());
                mOutputBean.setBeiZhu(new StringBuilder(beiZhu.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    StringBuilder beiZhu = new StringBuilder();

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels*1, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    boolean isDot = false;
    boolean isCul = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete:
                delete();
                break;
            case R.id.dot:
                if(!isDot&&!mBuilder.toString().equals("0")){
                    add('.');
                    isDot = true;
                }
                break;
            case R.id.zero:
                if(mBuilder.length()<=8) add('0');
                break;
            case R.id.one:
                if(mBuilder.length()<=8) add('1');
                break;
            case R.id.two:
                if(mBuilder.length()<=8) add('2');
                break;
            case R.id.three:
                if(mBuilder.length()<=8) add('3');
                break;
            case R.id.four:
                if(mBuilder.length()<=8) add('4');
                break;
            case R.id.five:
                if(mBuilder.length()<=8) add('5');
                break;
            case R.id.six:
                if(mBuilder.length()<=8) add('6');
                break;
            case R.id.seven:
                if(mBuilder.length()<=8) add('7');
                break;
            case R.id.eight:
                if(mBuilder.length()<=8)add('8');
                break;
            case R.id.nine:
                if(mBuilder.length()<=8)add('9');
                break;
            case R.id.finish:
                if(isCul){
                    int result = calculate(toInfixExpression(mBuilder.toString()));
                    mBuilder.delete(0, mBuilder.length());
                    mBuilder.append(result);
                    mBinding.money.setText(mBuilder.toString());

                    isCul = false;
                }else {
//                    mMainFragment.getList().clear();
//                    List<BillBean> billBeans = LitePal.findAll(BillBean.class);
//                    if (billBeans!=null){
//                        for (BillBean billBean : billBeans){
//                            if (billBean.getAccount().equals(account)){
//                                OutputBean outputBean = new OutputBean();
//                                outputBean.setName(billBean.getName());
//                                outputBean.setImageId(billBean.getImageId());
//                                outputBean.setDate(new StringBuilder().append(billBean.getDate()));
//                                outputBean.setBeiZhu(new StringBuilder().append(billBean.getBeiZhu()));
//                                outputBean.setMoneyCount(new StringBuilder().append(billBean.getMoneyCount()));
//                                mMainFragment.getList().add(outputBean);
//                            }
//                        }
//                    }

//                    List<BillBean> billBeans = LitePal.findAll(BillBean.class);
//
//                    weekBillBeanList = new ArrayList<>();
//
//
//                    for(BillBean billBean:billBeans){
//                        List<OutputBean> outputBeans = new ArrayList<>();
//                        for (BillBean billBean1 : billBeans){
//                            if (billBean1.getDate().equals(billBean.getDate())){
//                                if (billBean.getAccount().equals(account)&&billBean1.getAccount().equals(account)){
//                                    OutputBean outputBean = new OutputBean();
//                                    outputBean.setName(billBean1.getName());
//                                    outputBean.setImageId(billBean1.getImageId());
//                                    outputBean.setDate(new StringBuilder().append(billBean1.getDate()));
//                                    outputBean.setBeiZhu(new StringBuilder().append(billBean1.getBeiZhu()));
//                                    outputBean.setMoneyCount(new StringBuilder().append(billBean1.getMoneyCount()));
//                                    outputBeans.add(outputBean);
//                                }
//                            }
//                        }
//                        WeekBillBean weekBillBean = new WeekBillBean();
//                        weekBillBean.setWeekDate(billBean.getDate());
//                        weekBillBean.setWeekBillBeanList(outputBeans);
//                        weekBillBeanList.add(weekBillBean);
//                    }
                    List<BillBean> billBeans = LitePal.findAll(BillBean.class);

                    weekBillBeanList = new ArrayList<>();

                    //获取不重复的时间集合
                    List<String> mDateList = new ArrayList<>();
                    for (BillBean billBean : billBeans){
                        mDateList.add(billBean.getDate());
                    }
                    List<String> noRepeatList = new ArrayList<>(mDateList);

                    Set set = new HashSet(noRepeatList);

                    noRepeatList = new ArrayList<>(set);

                    int i= 0;
                    for (i = 0;i<noRepeatList.size();i++){
                        List<OutputBean> outputBeans = new ArrayList<>();
                        for (BillBean billBean : billBeans){
                            if (billBean.getDate().equals(noRepeatList.get(i))){
                                if (billBean.getAccount().equals(account)){
                                    OutputBean outputBean = new OutputBean();
                                    outputBean.setName(billBean.getName());
                                    outputBean.setImageId(billBean.getImageId());
                                    outputBean.setDate(new StringBuilder().append(billBean.getDate()));
                                    outputBean.setBeiZhu(new StringBuilder().append(billBean.getBeiZhu()));
                                    outputBean.setMoneyCount(new StringBuilder().append(billBean.getMoneyCount()));
                                    outputBeans.add(outputBean);
                                }
                            }
                        }
                        WeekBillBean weekBillBean = new WeekBillBean();
                        weekBillBean.setWeekDate(noRepeatList.get(i).substring(5,noRepeatList.get(i).length()));
                        weekBillBean.setWeekBillBeanList(outputBeans);
                        weekBillBeanList.add(weekBillBean);
                    }
                    mOutputBean.setMoneyCount(mBuilder);

                    int j = 0;
                    for (WeekBillBean weekBillBean:weekBillBeanList){
                        if (weekBillBean.getWeekDate().equals(mOutputBean.getDate().toString())){
                            weekBillBean.getWeekBillBeanList().add(mOutputBean);
                            j = 1;
                            break;
                        }
                    }
                    if (j == 0){
                        List<OutputBean> outputBeans = new ArrayList<>();
                        outputBeans.add(mOutputBean);
                        WeekBillBean weekBillBean = new WeekBillBean();
                        weekBillBean.setWeekDate(mOutputBean.getDate().toString());
                        weekBillBean.setWeekBillBeanList(outputBeans);
                        weekBillBeanList.add(weekBillBean);
                    }
//                    mMainFragment.getList().add(mOutputBean);
                    BillBean billBean = new BillBean();
                    billBean.setImageId(mOutputBean.getImageId());
                    billBean.setName(mOutputBean.getName());
                    billBean.setBeiZhu(mOutputBean.getBeiZhu().toString());
                    billBean.setMoneyCount(mOutputBean.getMoneyCount().toString());
                    billBean.setDate(mOutputBean.getDate().toString());
                    billBean.setAccount(account);
                    billBean.save();

//                    mViewModel.setBean(mMainFragment.getList());
                    mMainFragment.addData(weekBillBeanList);
                    getDialog().dismiss();
                    getActivity().finish();


                }

                break;
            case R.id.date:
                dataPicker();

                break;
            case R.id.add:
                if(mBuilder.length()<=8&&!isCul)add('+');
                isCul = true;
                break;
            case R.id.sub:
                if(mBuilder.length()<=8&&!isCul)add('-');
                isCul = true;
                break;
        }
    }


    StringBuilder date = new StringBuilder();
    private void dataPicker() {
        MaterialDatePicker<Long> datePicker= MaterialDatePicker.Builder.datePicker().setTitleText("日期选择").build();

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                String dateStr = DateFormat.getDateInstance().format(new Date(selection));
                date.append(dateStr);
                mBinding.dateIcon.setVisibility(View.GONE);
                mBinding.dateContent.setText(dateStr);
                mOutputBean.setDate(new StringBuilder(dateStr));
            }
        });
        datePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        datePicker.show(mFragmentManager,"data");
    }


    public static List<String> toInfixExpression(String string) {
        // 索引
        int index = 0;
        // 创建一个数组保存
        List<String> ls = new ArrayList<>();
        while (index < string.length()) {
            // 如果是字符那么就直接添加
            if (string.charAt(index) < 48 || string.charAt(index) > 57) {
                ls.add(string.charAt(index) + "");
                index++;
            } else {
                String str = "";
                // 判断数字是否是多位数，直到匹配不到数字结束循环
                while (index < string.length() && (string.charAt(index) >= 48 && string.charAt(index) <= 57)) {
                    // 拼接
                    str += string.charAt(index) + "";
                    index++;
                }
                ls.add(str);
            }
        }
        return ls;

    }

    public static int calculate(List<String> ls) {
        // 创建栈, 只需要一个栈即可
        Stack<String> stack = new Stack<String>();
        // 遍历 ls
        for (String item : ls) {
            // 这里使用正则表达式来取出数
            if (item.matches("\\d+")) { // 匹配的是多位数
                // 入栈
                stack.push(item);
            }
        }
        for (String item : ls) {
            // 这里使用正则表达式来取出数
            if (item.matches("\\d+")) { // 匹配的是多位数

            }else {
                // pop出两个数，并运算， 再入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                }
                //把res 入栈
                stack.push("" + res);
                //最后留在stack中的数据是运算结果
            }
        }

        return Integer.parseInt(stack.pop());
    }

    private void delete() {
        if (mBuilder.length() == 1) {
            mBuilder.setLength(mBuilder.length()-1);
            mBuilder.append("0");
            mBinding.money.setText("0");
        }else {
            mBuilder.setLength(mBuilder.length()-1);
            mBinding.money.setText(mBuilder);
        }
//        Log.d("HelloWorld", "delete: "+mBuilder.toString());

    }

    private void add(char s) {
//        Log.d("HelloWorld", "add: "+mBuilder.toString());
        if(mBuilder.toString().equals("0")){
            // Log.d("HelloWorld", "add: 1");
            mBuilder.setCharAt(0,s);
            mBinding.money.setText(mBuilder);
        }else {
           // Log.d("HelloWorld", "add:2 ");
            mBuilder.append(s);
            mBinding.money.setText(mBuilder);
        }

    }
}
