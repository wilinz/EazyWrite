package com.eazywrite.app.ui.bill.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eazywrite.app.R;
import com.eazywrite.app.databinding.FragmentBillMainBinding;
import com.eazywrite.app.ui.bill.AddBillContentActivity;
import com.eazywrite.app.ui.bill.adapter.CallbackData;
import com.eazywrite.app.ui.bill.adapter.ItemRecyclerViewAdapter;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainFragment extends Fragment implements View.OnClickListener, CallbackData {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill_main,container,false);

        return  mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    InputViewModel mViewModel;
    FragmentBillMainBinding mBinding;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.addItem.setOnClickListener(view1 -> {
            AddBillContentActivity.actionStart(getActivity(),this,null);
        });
        setOnClickListener();
    }

    private void setOnClickListener() {
        mBinding.timeChoose.setOnClickListener(this);
    }

    private void chooseDate() {
        new SingleDateAndTimePickerDialog.Builder(getContext())
                .bottomSheet()
                .curved()
                .displayMinutes(false)
                .displayHours(false)
                .displayDays(false)
                .displayMonth(true)
                .mainColor(ContextCompat.getColor(getContext(), R.color.black))
                .displayYears(true)
                .displayDaysOfMonth(true)
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        String dateStr = DateFormat.getDateInstance().format(date);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                        Date date1 = null;
                        try {
                            date1 = sdf.parse(dateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        int year = date1.getYear() + 1900; // 因为Date类中的getYear()方法返回的是自1900年以来的年数，所以要加上1900
                        int month = date1.getMonth() + 1; // 因为Date类中的getMonth()方法返回的是0到11之间的数字，所以要加1
                        int day = date1.getDate();
                        LocalDate date2 = null;
                        DayOfWeek dayOfWeek = null;
                        String chineseDayOfWeekString = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            date2 = LocalDate.of(2023, 2, 25);
                            dayOfWeek = date2.getDayOfWeek();
                            DayOfWeek dayOfWeek2 = DayOfWeek.valueOf(dayOfWeek.toString());
                            chineseDayOfWeekString = dayOfWeek2.getDisplayName(
                                    java.time.format.TextStyle.FULL_STANDALONE,
                                    Locale.CHINESE
                            );
                        }

                        mBinding.year.setText(year+"年");
                        mBinding.month.setText(month+"月");
                        mBinding.day.setText(month+"月"+day+"日"+" "+chineseDayOfWeekString);
                    }
                })
                .display();
    }
    
    
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.timeChoose:
                chooseDate();
                break;
                
        }
    }

    ArrayList<InputViewModel> mList = new ArrayList<>();


    @Override
    public void addData(InputViewModel viewModel) {
        mList.add(viewModel);
        for (InputViewModel i:mList)
            Log.d("HelloWorld", "addData: "+i.getBean().getValue().getName());
        mBinding.keepAccounts.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.keepAccounts.setAdapter(new ItemRecyclerViewAdapter(mList,getContext()));
    }
}