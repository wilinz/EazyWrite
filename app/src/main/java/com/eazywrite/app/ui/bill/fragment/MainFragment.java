package com.eazywrite.app.ui.bill.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eazywrite.app.R;
import com.eazywrite.app.data.model.BillBean;

import com.eazywrite.app.data.model.WeekBillBean;
import com.eazywrite.app.databinding.RestallMainBinding;
import com.eazywrite.app.ui.bill.AddBillContentActivity;
import com.eazywrite.app.ui.bill.adapter.BitemRecyclerViewAdapter;
import com.eazywrite.app.ui.bill.adapter.CallbackData;
import com.eazywrite.app.ui.bill.adapter.ItemRecyclerViewAdapter;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import org.litepal.LitePal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainFragment extends Fragment implements View.OnClickListener, CallbackData {

    private SharedPreferences pref;

    private SharedPreferences prefDate;

    private String sYear;
    private String sMonth;
    private String sDay;

    private String account;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.restall_main,container,false);

        return  mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    RestallMainBinding mBinding;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pref = getContext().getSharedPreferences("User", MODE_PRIVATE);
        account = pref.getString("account","");

        List<BillBean> billBeans = LitePal.findAll(BillBean.class);

        List<WeekBillBean> weekBillBeanList = new ArrayList<>();

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
        mBinding.keepAccounts.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.keepAccounts.setAdapter(new BitemRecyclerViewAdapter(weekBillBeanList,getContext()));



        mBinding.addItem.setOnClickListener(view1 -> {
            if (account.equals("")){
                Toast.makeText(getContext(),"请先登陆账号",Toast.LENGTH_SHORT).show();
            }else {
                AddBillContentActivity.actionStart(getActivity(),this,null);
            }
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
                                    TextStyle.FULL_STANDALONE,
                                    Locale.CHINESE
                            );
                        }

                        mBinding.year.setText(year+"年");
                        mBinding.month.setText(month+"月");
                        mBinding.day.setText(month+"月"+day+"日"+" "+chineseDayOfWeekString);

                        SharedPreferences.Editor editor = getContext().getSharedPreferences("date",MODE_PRIVATE).edit();
                        editor.putString("year",year+"");
                        editor.putString("month",month+"");
                        editor.putString("day",day+"");
                        editor.apply();

//                        Message message = new Message();
//                        message.what = 1;
//                        handler.sendMessage(message);
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

    public List<OutputBean> getList() {
        return mList;
    }

    public void setList(List<OutputBean> list) {
        mList = list;
    }

    public List<OutputBean> mList = new ArrayList<>();

    @Override
    public void addData(List<WeekBillBean> weekBillBeanList) {
        mBinding.keepAccounts.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.keepAccounts.setAdapter(new BitemRecyclerViewAdapter(weekBillBeanList,getContext()));
    }

//    Handler handler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(@NonNull Message message) {
//            if (message.what == 1){
//                prefDate = getContext().getSharedPreferences("date", MODE_PRIVATE);
//                sYear = prefDate.getString("year","");
//                sMonth = prefDate .getString("month","");
//                sDay = prefDate.getString("day","");
//
//                Log.d("TAGX", ""+sYear+" "+sMonth+ " "+sDay);
//                List<BillBean> billBeans = LitePal.findAll(BillBean.class);
//
//                List<OutputBean> outputBeans = new ArrayList<>();
//
//                if (billBeans!=null){
//                    for (BillBean billBean : billBeans){
//                        if (billBean.getAccount().equals(account)){
//                            //将2022年10月3日 截取三段 提取出 2022 10 3
//                            String str0 = billBean.getDate();
//                            Date d1 = null;//定义起始日期
//                            try {
//                                d1 = new SimpleDateFormat("yyyy年MM月dd日").parse(str0);
//                            } catch (ParseException e) {
//                                throw new RuntimeException(e);
//                            }
//                            SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
//
//                            SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
//
//                            SimpleDateFormat sdf2= new SimpleDateFormat("dd");
//
//                            String year = sdf0.format(d1);
//
//                            String month = sdf1.format(d1);
//
//                            String day = sdf2.format(d1);
//
//                            //去掉月份和日前面的零
//                            String newMonth = month.replaceFirst("^0*", "");
//                            String newDay = day.replaceFirst("^0*", "");
//
//
//
//                            if (year.equals(sYear)&&newMonth.equals(sMonth)&&newDay.equals(sDay)){
//                                OutputBean outputBean = new OutputBean();
//                                outputBean.setName(billBean.getName());
//                                outputBean.setImageId(billBean.getImageId());
//                                outputBean.setDate(new StringBuilder().append(billBean.getDate()));
//                                outputBean.setBeiZhu(new StringBuilder().append(billBean.getBeiZhu()));
//                                outputBean.setMoneyCount(new StringBuilder().append(billBean.getMoneyCount()));
//                                outputBeans.add(outputBean);
//                            }
//                        }
//                    }
//                    mBinding.keepAccounts.setLayoutManager(new LinearLayoutManager(getContext()));
//                    mBinding.keepAccounts.setAdapter(new ItemRecyclerViewAdapter(outputBeans,getContext()));
//                }
//
//            }
//            return false;
//        }
//    });
}
