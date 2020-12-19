package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class atv_login extends AppCompatActivity {

    TextInputEditText et_username,et_password;
    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_login);

        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);

        db= FirebaseDatabase.getInstance();
        ref = db.getReference("users");

        Button btn_login=findViewById(R.id.btn_login);
        Button
                btn_register=findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(atv_login.this,atv_register.class));
                overridePendingTransition(R.anim.anim_fade_in,R.anim.anim_fade_out);

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_password.length()==0 || et_username.length()==0)
                {
                    Toast.makeText(atv_login.this, "نام کاربری و رمز عبور را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                }

                login();
            }
        });


    }

    ArrayList<mdl_user> arrayList;
    boolean exist=false;
    private void login()
    {
        exist=false;
        arrayList=new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren())
                {
                    mdl_user user=snap.getValue(mdl_user.class);
                    if(user.username.equals(et_username.getText().toString()) && user.password.equals(et_password.getText().toString()))
                    {
                        exist=true;
                        startActivity(new Intent(atv_login.this,atv_note.class));
                    }

                }

                if(exist==false)
                {

                        Toast.makeText(atv_login.this, "نام کاربری یا رمز عبور اشتباه می باشد", Toast.LENGTH_SHORT).show();

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