package com.yojoo.skincancerclassifier.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yojoo.skincancerclassifier.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    private Button guestBtn;
    private TextView loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        guestBtn = findViewById(R.id.guest_btn);
        loginBtn = findViewById(R.id.Login_btn);

        guestBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.guest_btn:
                //a change happened in transsion to the main activity insted of the home activity
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.Login_btn:

                break;
        }

    }


    //    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }

}
