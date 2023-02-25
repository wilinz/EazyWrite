package com.eazywrite.app.ui.bill.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eazywrite.app.R;
import com.eazywrite.app.data.model.RegisterBean;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterFragment extends Fragment {

    private static final String CodeTypeRegister = "1001";
    private static final String CodeTypeResetPassword = "1002";
    private static final String CodeTypeLogin = "1003";
    private EditText editTextRgsEmail;
    private EditText editTextRgsPassword;
    private EditText editTextRgsNote;
    private Button buttonRgsReturn;
    private Button buttonRgsSend;
    private RelativeLayout relativeLayoutGo;
    private CheckBox checkBox;

    private RegisterBean registerBean;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_register,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextRgsEmail = view.findViewById(R.id.edittext_register1);
        editTextRgsPassword = view.findViewById(R.id.edittext_register2);
        editTextRgsNote = view.findViewById(R.id.edittext_register3);
        buttonRgsReturn = view.findViewById(R.id.button_register);
        buttonRgsSend = view.findViewById(R.id.button_register_send);
        relativeLayoutGo = view.findViewById(R.id.relativelayout_register_go);


        editTextRgsPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    editTextRgsPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    editTextRgsPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        //返回键
        buttonRgsReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //发送验证码
        buttonRgsSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextRgsEmail.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"请填写邮箱",Toast.LENGTH_SHORT).show();
                }else {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody
                            .Builder()
                            .add("codeType",CodeTypeRegister)
                            .add("graphicCode","随便")
                            .add("phoneOrEmail",editTextRgsEmail.getText().toString())
                            .build();
                    Request request = new Request
                            .Builder()
                            .url("https://home.wilinz.com:9994/account/verify")
                            .post(body)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {


                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String data = response.body().string();
                            Gson gson = new Gson();
                            registerBean = gson.fromJson(data,RegisterBean.class);
                            if (registerBean.getMsg().toString().equals("ok")){
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            }else {
                                Message message = new Message();
                                message.what = 2;
                                handler.sendMessage(message);
                            }
                        }
                    });
                }
            }
        });

        //注册
        relativeLayoutGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(editTextRgsEmail.getText().toString().equals("") &&editTextRgsPassword.getText().toString().equals("") && editTextRgsNote.getText().toString().equals(""))){
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody
                            .Builder()
                            .add("code",editTextRgsNote.getText().toString())
                            .add("password",editTextRgsPassword.getText().toString())
                            .add("username",editTextRgsEmail.getText().toString())
                            .build();

                    Request request = new Request
                            .Builder()
                            .url("https://home.wilinz.com:9994/account/register")
                            .post(body)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {


                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String data = response.body().string();
                            Gson gson = new Gson();
                            registerBean = gson.fromJson(data,RegisterBean.class);
                            if (registerBean.getMsg().toString().equals("ok")){
                                Message message = new Message();
                                message.what = 3;
                                handler.sendMessage(message);
                            }else {
                                Message message = new Message();
                                message.what = 4;
                                handler.sendMessage(message);
                            }
                        }
                    });

                }else {
                    Toast.makeText(getActivity(),"请填写所有信息",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (message.what == 1){
                Toast.makeText(getActivity(),"发送成功",Toast.LENGTH_SHORT).show();
            }else if (message.what == 2){
                Toast.makeText(getActivity(),"发送失败",Toast.LENGTH_SHORT).show();
            }else if (message.what == 3){
                Toast.makeText(getActivity(),"注册成功",Toast.LENGTH_SHORT).show();
            }else if (message.what == 4){
                Toast.makeText(getActivity(),"注册失败",Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });
}
