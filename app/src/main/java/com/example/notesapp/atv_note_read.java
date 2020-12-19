package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class atv_note_read extends AppCompatActivity {

    TextView tv_title,tv_text;
    FloatingActionButton btn_share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_note_read);

        tv_title=findViewById(R.id.tv_name);
        tv_text=findViewById(R.id.tv_text);

        btn_share=findViewById(R.id.btn_share);

        tv_title.setText(app_var.note.getTitle());
        tv_text.setText(app_var.note.getText());

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                String title = tv_title.getText().toString();
                String body = tv_text.getText().toString();
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, body);
                intent.putExtra(android.content.Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(intent, "اشتراک گذاری"));
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}