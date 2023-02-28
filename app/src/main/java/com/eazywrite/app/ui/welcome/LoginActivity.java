package com.eazywrite.app.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eazywrite.app.R;
import com.eazywrite.app.data.model.LoginBean;
import com.eazywrite.app.data.model.RegisterResponse;
import com.eazywrite.app.data.model.VerifyBean;
import com.eazywrite.app.data.network.Network;
import com.eazywrite.app.databinding.LoginInBinding;
import com.eazywrite.app.ui.main.MainActivity;
import com.google.gson.Gson;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this,R.layout.login_in);
        mActivity = this;
        mBinding.post.setOnClickListener(this);
        mBinding.login.setOnClickListener(this);
        initView();

    }

    private LoginInBinding mBinding;

    private void initView() {
        CardView cardView = findViewById(R.id.round1);
        cardView.getBackground().setAlpha(35);

        CardView cardView1 = findViewById(R.id.round2);
        cardView1.getBackground().setAlpha(20);

        TextView textView1 = (TextView)this.findViewById(R.id.login);
        TextView textView2 = (TextView)this.findViewById(R.id.gainYanZhenMa);
        AssetManager mgr = this.getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/msyhbd.ttf");
        textView1.setTypeface(tf);
        textView2.setTypeface(tf);
    }

    static WelcomeActivity sWelcomeActivity;
    static public void jumpLoginActivity(Context context,WelcomeActivity activity) {
        Intent intent = new Intent(context, LoginActivity.class);
        sWelcomeActivity = activity;
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post:
                post();
                break;
            case R.id.login:
                login();
                break;
        }
    }
    LoginActivity mActivity;

    private void login() {
        String password = String.valueOf(mBinding.password.getText());
        String verifyCode = String.valueOf(mBinding.verificationCode.getText());
        String postbox = String.valueOf(mBinding.postbox.getText());
        if(postbox.equals("")){
            showToast("邮箱不能为空");
            return;
        }else if(verifyCode.equals("")){
            showToast("验证码不能为空");
            return;
        }else if(password.equals("")){
            showToast("密码不能为空");
            return;
        }
        LoginBean bean = new LoginBean();
        bean.setPassword(password);
        bean.setCode(verifyCode);
        bean.setUsername(postbox);
        String json = new Gson().toJson(bean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8")
                ,json);
        Call<RegisterResponse> call = Network.INSTANCE.getRegisterService().postLogin(body);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.d("HelloWorld", "onResponse: "+"code"+response.body().getCode());
                if(response.body().getCode().equals(200)){
                    showToast("注册成功");
                    MainActivity.Companion.jumpMainActivity(mActivity);
                    mActivity.finish();
                    sWelcomeActivity.finish();
                }
                else if(response.body().getCode().equals(10011)) showToast("密码不符合要求，密码至少包含 数字和英文，长度8-20");
                else if(response.body().getCode().equals(10003)) showToast("用户已存在");
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });
    }

    private void post() {
        String postbox = String.valueOf(mBinding.postbox.getText());
        if(postbox.equals("")){
            showToast("输入邮箱不能为空");
            return;}
        VerifyBean bean =  new VerifyBean();
        bean.setCodeType("1001");
        bean.setGraphicCode("忽略");
        bean.setPhoneOrEmail(postbox);
        String json = new Gson().toJson(bean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8")
                ,json);
        Call<RegisterResponse> call = Network.INSTANCE.getRegisterService().postVerify(body);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.body().getCode().equals(200)) showToast("验证码发送成功");
                    else showToast("验证码发送失败");
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });
    }

    public void showToast(String text) {
        Toast toast=Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}