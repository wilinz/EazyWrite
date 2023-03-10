package com.eazywrite.app.ui.welcome;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import com.eazywrite.app.MyApplication;
import com.eazywrite.app.R;
import com.eazywrite.app.data.model.RegisterResponse;
import com.eazywrite.app.data.model.SignUpBean;
import com.eazywrite.app.data.network.Network;
import com.eazywrite.app.databinding.FragmentLoginBinding;
import com.eazywrite.app.ui.main.MainActivity;
import com.eazywrite.app.util.ActivityKt;
import com.eazywrite.app.util.MessageSummaryKt;
import com.eazywrite.app.util.ShowToast;
import com.google.gson.Gson;

import org.jetbrains.annotations.Nullable;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityKt.setWindow(this, false);
        this.setContentView(R.layout.fragment_login);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_login);
        binding.signUp.setOnClickListener(this);
        mActivity = this;
        initView();


    }

    FragmentLoginBinding binding;

    private void initView() {
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
                LoginActivity.jumpLoginActivity(this, this);
                break;
            case R.id.signUp:
                loginUp();
                break;
        }
    }

    WelcomeActivity mActivity;

    private void loginUp() {
        String account = String.valueOf(binding.account.getText());
        String password = String.valueOf(binding.passwordWelcome.getText());
        if (account.equals("")) {
            ShowToast.showToast(getApplicationContext(), "?????????????????????");
            return;
        } else if (password.equals("")) {
            ShowToast.showToast(getApplicationContext(), "??????????????????");
            return;
        }

        SignUpBean signUpBean = new SignUpBean();
        signUpBean.setPassword(MessageSummaryKt.messageSummary(password, "SHA-256"));
        signUpBean.setUsername(account);
        String json = new Gson().toJson(signUpBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8")
                , json);
        Call<RegisterResponse> call = Network.INSTANCE.getAccountService().postLogin(body);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                response.body().getAll();
                if (response.body().getCode().equals(200)) {
                    SharedPreferences.Editor editor = getSharedPreferences("User", MODE_PRIVATE).edit();
                    editor.putString("account", account);
                    editor.apply();
                    ShowToast.showToast(getApplicationContext(), "????????????");
                    MainActivity.Companion.jumpMainActivity(mActivity);
                    mActivity.finish();
                } else if (response.body().getCode().equals(10007)) {
                    ShowToast.showToast(getApplicationContext(), "???????????????");
                } else if (response.body().getCode().equals(10005)) {
                    ShowToast.showToast(getApplicationContext(), "????????????");
                } else if (response.body().getCode().equals(10004)) {
                    ShowToast.showToast(getApplicationContext(), "????????????????????????");
                } else if (response.body().getCode().equals(10002)) {
                    ShowToast.showToast(getApplicationContext(), "????????????????????????5????????????1??????????????????");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });
    }
}
