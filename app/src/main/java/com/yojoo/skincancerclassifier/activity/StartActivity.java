package com.yojoo.skincancerclassifier.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.yojoo.skincancerclassifier.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;

    private Button guestBtn,signupBtn;
    private TextView loginBtn;
    private EditText emailEdt,passwordEdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mAuth = FirebaseAuth.getInstance();

        guestBtn = findViewById(R.id.guest_btn);
        loginBtn = findViewById(R.id.Login_btn);
        signupBtn = findViewById(R.id.register_btn);
        emailEdt = findViewById(R.id.email_edit);
        passwordEdt = findViewById(R.id.password_edit);

        guestBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);


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
                login();

                break;
            case R.id.register_btn:
                signUp();
                break;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null)
            loginSuccess();
    }
    private void loginSuccess(){
        if((mAuth.getCurrentUser()).isEmailVerified()) {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Please verify your Email.", Toast.LENGTH_SHORT).show();
        }
    }
    private void login(){
        String usernameVal = emailEdt.getText().toString();
        String passwordVal = passwordEdt.getText().toString();
        if (usernameVal.isEmpty()){
            emailEdt.setError("Enter username");
            emailEdt.requestFocus();
        }else if (passwordVal.isEmpty()){
            passwordEdt.setError("Enter Password");
            passwordEdt.requestFocus();
        }
        if(!usernameVal.equals("")&&!passwordVal.equals("")){
            mAuth.signInWithEmailAndPassword(usernameVal, passwordVal)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loginSuccess();
                            } else {
                                Toast.makeText(StartActivity.this, "Email or password isn't correct.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void signUp(){
        Intent intent = new Intent(StartActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }


    //    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }

}
