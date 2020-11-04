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

public class MainActivity extends AppCompatActivity {
    EditText umail,upass;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        umail = findViewById(R.id.umail);
        upass = findViewById(R.id.upass);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()!=null){
            startActivity(new Intent(this,HomeActivity.class));
            finish();
        }
    }

    public void login(View view) {
        String mail = umail.getText().toString();
        String pass = upass.getText().toString().trim();
        if (mail.isEmpty()|pass.isEmpty()){
            Toast.makeText(this, "Fill all the details",
                    Toast.LENGTH_SHORT).show();
        }else {
            auth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()){
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            finish();
                         }
                         else{
                             Toast.makeText(MainActivity.this, "Failed to Authenticate",
                                     Toast.LENGTH_SHORT).show();
                         }
                        }
                    });
        }
    }

    public void register(View view) {
        startActivity(new Intent(this,SecondActivity.class));
    }
}