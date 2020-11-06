package com.example.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {
    EditText number,otp;
    FirebaseAuth auth;
    /*To get the Otp ,to auto verify with the otp and to capture the failures*/
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    /*This is used to resend the Otp*/
    PhoneAuthProvider.ForceResendingToken token;
    /*To hold the verification id*/
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        number = findViewById(R.id.number);
        otp = findViewById(R.id.otp);
        auth = FirebaseAuth.getInstance();

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                /*verification id*/
                id = s;
                /*Token to resend the otp*/
                token = forceResendingToken;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                /*This is to perform auto verification*/
                signPhoneAuth(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(PhoneActivity.this, ""+e,
                        Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void signPhoneAuth(PhoneAuthCredential phoneAuthCredential) {
        auth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(PhoneActivity.this,HomeActivity.class));
                            finish();
                        }else{
                            Toast.makeText(PhoneActivity.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void send(View view) {
        String num = number.getText().toString().trim();
        if (num.isEmpty()){
            number.setError("Can't be empty");
        }else{
           /* PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+num,
                    60, TimeUnit.SECONDS, this,callbacks);*/
            PhoneAuthOptions options = PhoneAuthOptions
                    .newBuilder(auth).setPhoneNumber("+91"+num)
                    .setTimeout(60L,TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(callbacks).build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        }
    }

    public void verify(View view) {
        String code = otp.getText().toString().trim();
        if (code.isEmpty()){
            Toast.makeText(this, "Invalid code", Toast.LENGTH_SHORT).show();
        }
        else{
            /*To authenticate Manually*/
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id,code);
            signPhoneAuth(credential);
        }
    }

    public void resend(View view) {
        String num = number.getText().toString().trim();
        if (num.isEmpty()){
            number.setError("Can't be empty");
        }else{
           /* PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+num,
                    60, TimeUnit.SECONDS, this,callbacks);*/
            PhoneAuthOptions options = PhoneAuthOptions
                    .newBuilder(auth).setPhoneNumber("+91"+num)
                    .setTimeout(60L,TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(callbacks)
            .setForceResendingToken(token).build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        }
    }
}