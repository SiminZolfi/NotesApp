package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.divyanshu.colorseekbar.ColorSeekBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class atv_note_edit extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference ref;
    TextInputEditText et_title,et_text,et_password;
    FloatingActionButton btn_save;
    ColorSeekBar seekBar;
    int color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_note_edit);

        db= FirebaseDatabase.getInstance();
        ref = db.getReference("notes").child(String.valueOf(app_var.note.getId()));

        color= Color.rgb(255,255,255);
        et_title=findViewById(R.id.et_title);
        et_text=findViewById(R.id.et_text);
        et_password=findViewById(R.id.et_password);
        btn_save=findViewById(R.id.btn_save);
        seekBar=findViewById(R.id.seekbar);

        seekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int i) {
                color=i;
            }
        });

        et_text.setText(app_var.note.getText());
        et_title.setText(app_var.note.getTitle());
        et_password.setText(app_var.note.getPassword());
        color= Integer.parseInt(app_var.note.getColor());

        btn_save=findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_title.length()==0 || et_text.length()==0)
                {
                    Toast.makeText(atv_note_edit.this, "لطفا عنوان و متن یادداشت را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                }

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                String time = df.format(c.getTime());

                mdl_note mdl=new mdl_note();
                mdl.setId(app_var.note.getId());
                mdl.setDate(app_var.note.getDate());
                mdl.setUser_id(app_var.note.getUser_id());
                mdl.setTime(app_var.note.getTime());
                mdl.setTitle(et_title.getText().toString());
                mdl.setText(et_text.getText().toString());
                mdl.color=String.valueOf(color);

                if(et_password.length()>0)
                {
                    mdl.setPassword(et_password.getText().toString());
                    mdl.setLocked(1);
                }
                else
                {
                    mdl.setPassword(et_password.getText().toString());
                    mdl.setLocked(0);
                }

                ref.setValue(mdl);

                Toast.makeText(atv_note_edit.this, "یادداشت شما ویرایش گردید", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(atv_note_edit.this,atv_note.class));
                overridePendingTransition(R.anim.anim_fade_in,R.anim.anim_fade_out);
                finish();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}