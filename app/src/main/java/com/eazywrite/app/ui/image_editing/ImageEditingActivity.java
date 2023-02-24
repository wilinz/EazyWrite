package com.eazywrite.app.ui.image_editing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.eazywrite.app.R;
import com.eazywrite.app.util.ActivityKt;

public class ImageEditingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityKt.setWindow(this);
        setContentView(R.layout.activity_image_editing);

    }
}