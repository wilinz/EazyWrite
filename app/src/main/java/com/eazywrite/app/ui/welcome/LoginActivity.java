package com.eazywrite.app.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.eazywrite.app.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_in);
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

    static public void jumpLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}