package com.yojoo.skincancerclassifier.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yojoo.skincancerclassifier.R;

public class SignUpActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private EditText email,pass,confirmPass;
    private Button SignUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailUp);
        pass = findViewById(R.id.passwordUp);
        confirmPass = findViewById(R.id.passwordUpConfirm);
        SignUpBtn = findViewById(R.id.signUp);
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }


    public void signUp(){
        String emailStr = email.getText().toString();
        String passStr = pass.getText().toString();
        String confirmPassStr = confirmPass.getText().toString();
        if(emailStr.isEmpty()||passStr.isEmpty()||confirmPassStr.isEmpty()){
            Toast.makeText(this, "ALl fields are required", Toast.LENGTH_SHORT).show();
        }else {
            if(passStr.equals(confirmPassStr)){

                mAuth.createUserWithEmailAndPassword(emailStr, passStr)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    final FirebaseUser user = mAuth.getCurrentUser();
                                    user.sendEmailVerification()
                                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(SignUpActivity.this,
                                                                "Verification email sent to " + user.getEmail(),
                                                                Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(SignUpActivity.this, StartActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(SignUpActivity.this,"Can't send verification Email."
                                                                ,
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Can't sign up with this email.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else
                Toast.makeText(this, "Passwords aren't equal", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignUpActivity.this,StartActivity.class);
        startActivity(intent);
        finish();

    }
}
