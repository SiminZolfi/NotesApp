package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_note);

        rv_data=findViewById(R.id.rv_data);
        tv_name=findViewById(R.id.tv_name);

        tv_name.setText(app_var.user.getName());

        db= FirebaseDatabase.getInstance();
        ref = db.getReference("notes");


        load();

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