package com.example.firedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText name,roll,number;
    RecyclerView rv;
    DatabaseReference reference;
    List<MyModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        roll = findViewById(R.id.roll);
        number = findViewById(R.id.phone);
        rv = findViewById(R.id.rv);
        list = new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("Student");
        /*To read the data from the firebase database*/
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    MyModel myModel=dataSnapshot.getValue(MyModel.class);
                    list.add(myModel);
                }
                MyAdapter adapter = new MyAdapter(MainActivity.this,list);
                rv.setAdapter(adapter);
                rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rv.addItemDecoration(new DividerItemDecoration(MainActivity.this,
                        DividerItemDecoration.VERTICAL));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to fetch",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void save(View view) {
        String sname = name.getText().toString();
        String sroll = roll.getText().toString();
        String snumber = number.getText().toString();
        MyModel myModel = new MyModel(sname,sroll,snumber);
        /*To Write the data into firebase database*/
        /*reference.setValue(myModel)*/
       /* reference.child(reference.push().getKey()).setValue(myModel);*/
        reference.child(sroll).setValue(myModel);
        Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
    }
}