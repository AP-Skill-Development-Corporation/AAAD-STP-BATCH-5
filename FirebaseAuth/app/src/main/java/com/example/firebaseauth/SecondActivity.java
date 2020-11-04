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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {
    EditText mail,pass;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mail = findViewById(R.id.rmail);
        pass = findViewById(R.id.rpass);
        auth = FirebaseAuth.getInstance();
    }

    public void register(View view) {
        String umail = mail.getText().toString();
        String upass = pass.getText().toString();
        if (umail.isEmpty()|upass.isEmpty()){
            Toast.makeText(this, "Fill all the details",
                    Toast.LENGTH_SHORT).show();
        }else if (upass.length()<6){
            pass.setError("Length must be 6");
        }else {
            auth.createUserWithEmailAndPassword(umail,upass).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(SecondActivity.this,MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(SecondActivity.this, "Registration Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}