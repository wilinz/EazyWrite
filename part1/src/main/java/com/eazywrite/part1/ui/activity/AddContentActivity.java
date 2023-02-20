package com.eazywrite.part1.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.eazywrite.app.part1.R;
import com.eazywrite.part1.ui.fragment.AddItemFragment;

public class AddContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);

        addFragment();
    }

    private void addFragment() {
        AddItemFragment fragment = new AddItemFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.iddFragment,fragment);
        transaction.commit();
    }

    public static void actionStart(Context context, String data1, String data2){
        Intent intent = new Intent(context,AddContentActivity.class);
        intent.putExtra("param1",data1);
        intent.putExtra("param2",data2);
        context.startActivity(intent);
    }
}