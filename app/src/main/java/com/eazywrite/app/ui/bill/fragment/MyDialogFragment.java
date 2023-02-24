package com.eazywrite.app.ui.bill.fragment;

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
import androidx.lifecycle.ViewModelProvider;

import com.eazywrite.app.R;
import com.eazywrite.app.databinding.DialogFragmentBinding;
import com.eazywrite.app.ui.bill.AddBillContentActivity;

public class MyDialogFragment extends DialogFragment implements View.OnClickListener {
    private AddBillContentActivity mActivity;
    private FragmentManager mFragmentManager;
    public MyDialogFragment(AddBillContentActivity activity, FragmentManager fragmentManager) {
        mActivity = activity;
        mFragmentManager = fragmentManager;
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
        mBinding.beiZhu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                beiZhu.replace(0, beiZhu.length(), charSequence.toString());
                Log.d("HelloWorld", "onTextChanged: "+beiZhu.toString());
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
                getDialog().dismiss();
                break;
            case R.id.date:
                MyDatePickerFragment fragment = MyDatePickerFragment.newInstance();
                fragment.show(mFragmentManager,"datePicker");
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
