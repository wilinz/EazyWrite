package com.eazywrite.app.ui.welcome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.eazywrite.app.R;
import com.eazywrite.app.data.model.RegisterResponse;
import com.eazywrite.app.data.model.ResetBean;
import com.eazywrite.app.data.model.VerifyBean;
import com.eazywrite.app.data.network.Network;
import com.eazywrite.app.databinding.ActivityFogetPasswordBinding;
import com.eazywrite.app.ui.main.MainActivity;
import com.eazywrite.app.util.ActivityKt;
import com.google.gson.Gson;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityKt.setWindow(this, false);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_foget_password);
        setOnClickListener();
    }

    private void setOnClickListener() {
        mBinding.loginAndLogUp.setOnClickListener(this);
        mBinding.gain.setOnClickListener(this);
    }

    ActivityFogetPasswordBinding mBinding;
    static public void jumpForgetActivity(Context context) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginAndLogUp:
                loginAndLogUp();
                break;
            case R.id.gain:
                post();
                break;
        }
    }

    private void loginAndLogUp() {
        String postbox = String.valueOf(mBinding.postboxTwo.getText());
        if(postbox.equals("")){
            showToast("????????????????????????");
            return;}
        String code = String.valueOf(mBinding.code.getText());
        if (code.equals("")) {
            showToast("?????????????????????");
            return;}
        String passwordTwo = String.valueOf(mBinding.passwordTwo.getText());
        if (passwordTwo.equals("")) {
            showToast("?????????????????????");
            return;}
        String passwordThree = String.valueOf(mBinding.passwordThree.getText());
        if (passwordThree.equals("")) {
            showToast("?????????????????????????????????");
            return;}
        if (!passwordThree.equals(passwordTwo)) {
            showToast("??????????????????????????????");
            return;
        }

        ResetBean resetBean = new ResetBean();
        resetBean.setCode(code);
        resetBean.setUsername(postbox);
        resetBean.setNewPassword(passwordThree);
        resetBean.getAll();
        String json = new Gson().toJson(resetBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8")
                ,json);

        Call<RegisterResponse> call = Network.INSTANCE.getAccountService().postReset(body);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                response.body().getAll();
                if(response.body().getCode().equals(200)){
                    showToast("??????????????????");
                    SharedPreferences sharedPreferences = getSharedPreferences("password", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("password",passwordThree).apply();
                    MainActivity.Companion.jumpMainActivity(mForgetPasswordActivity);
                    mForgetPasswordActivity.finish();
                }
                else {
                    showToast(response.body().getMsg());
                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });
    }

    ForgetPasswordActivity mForgetPasswordActivity = this;


    private void post() {
        String postbox = String.valueOf(mBinding.postboxTwo.getText());
        if(postbox.equals("")){
            showToast("????????????????????????");
            return;}
        VerifyBean bean =  new VerifyBean();
        bean.setCodeType("1002");
        bean.setGraphicCode("??????");
        bean.setPhoneOrEmail(postbox);
        String json = new Gson().toJson(bean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8")
                ,json);
        Call<RegisterResponse> call = Network.INSTANCE.getAccountService().postVerify(body);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.body().getCode().equals(200)) showToast("?????????????????????");
                else showToast("?????????????????????");
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