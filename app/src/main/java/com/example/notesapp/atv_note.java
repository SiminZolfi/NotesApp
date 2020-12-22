package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class atv_note extends AppCompatActivity {
    RecyclerView rv_data;
    FirebaseDatabase db;
    DatabaseReference ref;
    TextView tv_name;
    FloatingActionButton btn_add;
    EditText et_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_note);

        rv_data=findViewById(R.id.rv_data);
        tv_name=findViewById(R.id.tv_name);
        btn_add=findViewById(R.id.btn_add);
        et_search=findViewById(R.id.et_search);

        tv_name.setText(app_var.user.getName());

        db= FirebaseDatabase.getInstance();
        ref = db.getReference("notes");


        load();


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(atv_note.this,atv_note_add.class));
                overridePendingTransition(R.anim.anim_fade_in,R.anim.anim_fade_out);

            }
        });
    }

    private void filter()
    {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList=new ArrayList<>();
                for(DataSnapshot snap:snapshot.getChildren())
                {
                    mdl_note mdl=snap.getValue(mdl_note.class);
                    if(mdl.user_id==app_var.user.getId() && mdl.title.contains(et_search.getText().toString()))
                    {
                        arrayList.add(mdl);
                    }
                }

                adp_note adapter=new adp_note(atv_note.this,arrayList);
                rv_data.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                rv_data.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    ArrayList<mdl_note> arrayList;
    private void load()
    {

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList=new ArrayList<>();
                for(DataSnapshot snap:snapshot.getChildren())
                {
                    mdl_note mdl=snap.getValue(mdl_note.class);
                    if(mdl.user_id==app_var.user.getId())
                    {
                        arrayList.add(mdl);
                    }
                }

                adp_note adapter=new adp_note(atv_note.this,arrayList);
                rv_data.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                rv_data.setAdapter(adapter);
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