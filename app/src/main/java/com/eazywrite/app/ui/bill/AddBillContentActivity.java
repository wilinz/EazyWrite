package com.eazywrite.app.ui.bill;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.eazywrite.app.R;
import com.eazywrite.app.ui.bill.fragment.AddItemFragment;
import com.eazywrite.app.util.ActivityKt;


public class AddBillContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill_content);

        ActivityKt.setWindow(this);

        addFragment();
    }

    private void addFragment() {
        AddItemFragment fragment = new AddItemFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.iddFragment, fragment);
        transaction.commit();
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, AddBillContentActivity.class);
        intent.putExtra("param1", data1);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }
}