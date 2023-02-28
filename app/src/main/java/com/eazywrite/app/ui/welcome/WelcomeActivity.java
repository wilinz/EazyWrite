package com.eazywrite.app.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eazywrite.app.R;
import com.eazywrite.app.databinding.FragmentWelcomeBinding;
import com.eazywrite.app.util.ActivityKt;

import org.jetbrains.annotations.Nullable;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener
{
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityKt.setWindow(this);
        this.setContentView(R.layout.fragment_welcome);
        FragmentWelcomeBinding binding = DataBindingUtil.setContentView(this,R.layout.fragment_welcome);


        AssetManager mgr = this.getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/msyhbd.ttf");
        binding.textView1.setTypeface(tf);
        binding.textView2.setTypeface(tf);

        CardView cardView = findViewById(R.id.round1);
        cardView.getBackground().setAlpha(35);

        CardView cardView1 = findViewById(R.id.round2);
        cardView1.getBackground().setAlpha(20);

        binding.forget.setOnClickListener(this);
        binding.loginPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget:
                ForgetPasswordActivity.jumpForgetActivity(this);
                break;
            case R.id.loginPassword:
                LoginActivity.jumpLoginActivity(this);
                break;
        }
    }
}
