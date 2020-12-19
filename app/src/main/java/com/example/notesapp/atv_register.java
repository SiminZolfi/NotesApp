package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class atv_register extends AppCompatActivity {

    TextInputEditText et_username,et_password,et_name;

    FirebaseDatabase db;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_register);

        Button btn_back=findViewById(R.id.btn_back);


        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        et_name=findViewById(R.id.et_name);

        db= FirebaseDatabase.getInstance();
        ref = db.getReference("users");

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(atv_register.this,atv_login.class));
                overridePendingTransition(R.anim.anim_fade_in,R.anim.anim_fade_out);
                finish();
            }
        });

        Button btn_register=findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_password.length()==0 || et_username.length()==0)
                {
                    Toast.makeText(atv_register.this, "ورود تمامی فیلدها الزامی است", Toast.LENGTH_SHORT).show();
                    return;
                }

                register();
            }
        });
    }


    ArrayList<mdl_user> arrayList;
    boolean exist=false;

    private void register()
    {
        exist=false;
        arrayList=new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren())
                {
                    mdl_user user=snap.getValue(mdl_user.class);
                    if(user.username.equals(et_username.getText().toString()))
                    {
                        exist=true;
                        Toast.makeText(atv_register.this, "نام کاربری تکراری می باشد", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                if(exist==false)
                {
                        final int min = 111111;
                        final int max = 999999;
                        final int id = new Random().nextInt((max - min) + 1) + min;

                        mdl_user mdl=new mdl_user();
                        mdl.setId(id);
                        mdl.setUsername(et_username.getText().toString());
                        mdl.setPassword(et_password.getText().toString());
                        mdl.setName(et_name.getText().toString());

                        ref.child(String.valueOf(id)).setValue(mdl);


                        Toast.makeText(atv_register.this, "حساب کاربری شما ایجاد گردید", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(atv_register.this,atv_login.class));
                        finish();
                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}