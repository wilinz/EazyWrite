package com.eazywrite.app.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.eazywrite.app.R;
import com.eazywrite.app.util.ActivityKt;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_chat);
        ActivityKt.setWindow(this);



    }
}