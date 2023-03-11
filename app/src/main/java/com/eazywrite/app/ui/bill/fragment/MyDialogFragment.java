package com.eazywrite.app.ui.bill.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
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
import com.eazywrite.app.data.model.Order;
import com.eazywrite.app.data.model.WeekBillBean;
import com.eazywrite.app.databinding.DialogFragmentBinding;
import com.eazywrite.app.ui.bill.AddBillContentActivity;
import com.eazywrite.app.ui.bill.adapter.ItemRecyclerViewAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.litepal.LitePal;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class MyDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    DialogFragmentBinding mBinding;

    StringBuilder mBuilder = new StringBuilder("0");

    boolean isDot = false;
    boolean isCul = false;
    private  AddItemFragment mAddItemFragment;

    public MyDialogFragment(AddItemFragment addItemFragment) {
        mAddItemFragment = addItemFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        getDialog().getWindow().setDimAmount(0f);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        mBinding.mul.setOnClickListener(this);
        mBinding.div.setOnClickListener(this);
        mBinding.clear2.setOnClickListener(this);
        mBinding.add.setOnClickListener(this);
        mBinding.sub.setOnClickListener(this);
    }


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
                if(mBuilder.length()==0)return;
                char temp = mBuilder.toString().charAt(0);
                if(temp=='+'|temp=='*'|temp=='/'|temp=='-'){
                    mBuilder.deleteCharAt(0);
                    mBinding.money.setText(mBuilder.toString());
                    return;
                }else if(temp=='.'){
                    mBuilder.deleteCharAt(0);
                    mBinding.money.setText("0");
                    isCul=false;
                    isDot=false;
                }
                if(isCul){
                    Double result = calculate(toInfixExpression(mBuilder.toString()));
                    mBuilder.delete(0, mBuilder.length());
                    mBuilder.append(result);
                    mBinding.money.setText(mBuilder.toString());

                    isCul = false;
                }else {
                    getDialog().dismiss();
                    mAddItemFragment.getCount(mBuilder.toString());
                }

                break;
            case R.id.add:
                if(mBuilder.length()<=8&&!isCul)add('+');
                isCul = true;
                break;
            case R.id.sub:
                if(mBuilder.length()<=8&&!isCul)add('-');
                isCul = true;
                break;
            case R.id.mul:
                if(mBuilder.length()<=8&&!isCul)add('*');
                isCul = true;
                break;
            case R.id.div:
                if(mBuilder.length()<=8&&!isCul)add('/');
                isCul = true;
                break;
            case R.id.clear2:
                mBuilder.delete(0,mBuilder.length());
                mBinding.money.setText("0");
                isCul=false;
                isDot=false;
                break;
        }
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

    }


    public static List<String> toInfixExpression(String string) {
        int index = 0;
        List<String> ls = new ArrayList<>();
        while (index < string.length()) {
            if (string.charAt(index) == '+' || string.charAt(index) == '-'
                    || string.charAt(index) == '*' || string.charAt(index) == '/') {
                ls.add(string.charAt(index) + "");
                index++;
            } else {
                String str = "";
                while (index < string.length() && ((string.charAt(index) >= '0' && string.charAt(index) <= '9') || string.charAt(index) == '.')) {
                    str += string.charAt(index) + "";
                    index++;
                }
                ls.add(str);
            }
        }
        return ls;
    }

    public static double calculate(List<String> ls) {
        Stack<String> stack = new Stack<String>();
        for (String item : ls) {
            if (item.matches("\\d+\\.?\\d*")) {
                stack.push(item);
            }
        }
        for (String item : ls) {
            if (item.matches("\\d+\\.?\\d*")) {

            } else {
                double num2 = Double.parseDouble(stack.pop());
                double num1 = Double.parseDouble(stack.pop());
                double res = 0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                } else if (item.equals("*")) {
                    res = num1 * num2;
                } else if (item.equals("/")) {
                    res = num1 / num2;
                    BigDecimal bg = new BigDecimal(res);

                    res = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                stack.push("" + res);
            }
        }

        return Double.parseDouble(stack.pop());
    }

    private void add(char s) {
        if (mBuilder.toString().equals("0")) {
            mBuilder.setCharAt(0, s);
            mBinding.money.setText(mBuilder);
        } else {
            mBuilder.append(s);
            mBinding.money.setText(mBuilder);
        }
    }

}
