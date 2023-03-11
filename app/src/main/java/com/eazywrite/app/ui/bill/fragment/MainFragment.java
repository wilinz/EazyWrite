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

import com.eazywrite.app.data.model.Order;
import com.eazywrite.app.data.model.WeekBillBean;
import com.eazywrite.app.databinding.RestallMainBinding;
import com.eazywrite.app.ui.bill.AddBillContentActivity;
import com.eazywrite.app.ui.bill.adapter.BitemRecyclerViewAdapter;
import com.eazywrite.app.ui.bill.adapter.CallbackData;
import com.eazywrite.app.ui.bill.adapter.ItemRecyclerViewAdapter;
import com.eazywrite.app.util.ActivityKt;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import org.litepal.LitePal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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


        //不重复时间集合的时间戳
        List<Long> mTimeStr  = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

        int p = 0;
        for (p = 0;p < noRepeatList.size();p++){
            Long timeStr = null;
            try {
                timeStr = sdf.parse(noRepeatList.get(p)).getTime();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            //获取到时间戳集合
            mTimeStr.add(timeStr);
        }


        //排序时间戳集合，由大到小
        List<Order> orders = new ArrayList<>();
        int k = 0;
        for (k=0;k<mTimeStr.size();k++){
            Order order = new Order();
            order.setDate(mTimeStr.get(k));
            orders.add(order);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            orders.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));
        }

        //获取排序后的时间戳
        List<Long> mNewTimeStr  = new ArrayList<>();
        for (Order order:orders){
            Long newTimeStr = order.getDate();
            mNewTimeStr.add(newTimeStr);
        }

        //将排序好的时间戳再次转为String类型
        List<String> strTimesList = new ArrayList<>();
        int x =0;
        for (x = 0;x<mNewTimeStr.size();x++){
            String strTimes = sdf.format(mNewTimeStr.get(x));
            strTimesList.add(strTimes);
        }

        //去掉月日前面多余的0,不然会影响与数据库中取出的日期对比
        noRepeatList.clear();
        Date d1 = null;//定义起始日期
        for (x = 0;x<strTimesList.size();x++){
            try {
                d1 = new SimpleDateFormat("yyyy年MM月dd日").parse(strTimesList.get(x));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");

            SimpleDateFormat sdf1 = new SimpleDateFormat("MM");

            SimpleDateFormat sdf2= new SimpleDateFormat("dd");

            String year = sdf0.format(d1);

            String month = sdf1.format(d1);

            String day = sdf2.format(d1);

            //去掉月份和日前面的零
            String newMonth = month.replaceFirst("^0*", "");
            String newDay = day.replaceFirst("^0*", "");
            String newDate = year + "年" + newMonth + "月" + newDay + "日";
            Log.d("TAGX", ""+newDate);
            noRepeatList.add(newDate);
        }

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

                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
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

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (message.what == 1){
                prefDate = getContext().getSharedPreferences("date", MODE_PRIVATE);
                sYear = prefDate.getString("year","");
                sMonth = prefDate .getString("month","");
                sDay = prefDate.getString("day","");

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
                    weekBillBean.setWeekDate(noRepeatList.get(i));
                    weekBillBean.setWeekBillBeanList(outputBeans);
                    weekBillBeanList.add(weekBillBean);
                }
                List<WeekBillBean> weekBillBeanList1 = new ArrayList<>();
                for (WeekBillBean weekBillBean : weekBillBeanList){
                    String str0 = weekBillBean.getWeekDate();
                            Date d1 = null;//定义起始日期
                            try {
                                d1 = new SimpleDateFormat("yyyy年MM月dd日").parse(str0);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }

                            SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");

                            SimpleDateFormat sdf1 = new SimpleDateFormat("MM");

                            SimpleDateFormat sdf2= new SimpleDateFormat("dd");

                            String year = sdf0.format(d1);

                            String month = sdf1.format(d1);

                            String day = sdf2.format(d1);

                            //去掉月份和日前面的零
                            String newMonth = month.replaceFirst("^0*", "");
                            String newDay = day.replaceFirst("^0*", "");

                            if (newMonth.equals(sMonth)&&newDay.equals(sDay)&&year.equals(sYear)){
                                weekBillBean.setWeekDate(weekBillBean.getWeekDate().substring(5,weekBillBean.getWeekDate().length()));
                                weekBillBeanList1.add(weekBillBean);
                            }
                }
                mBinding.keepAccounts.setLayoutManager(new LinearLayoutManager(getContext()));
                mBinding.keepAccounts.setAdapter(new BitemRecyclerViewAdapter(weekBillBeanList1,getContext()));


            }
            return false;
        }
    });
}
