package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class adp_note extends RecyclerView.Adapter<adp_note.ViewHolder> {
    Context ctx;
    ArrayList<mdl_note> list;
    FirebaseDatabase database;
    DatabaseReference ref;
    public adp_note(Context ctx, ArrayList<mdl_note> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from((parent).getContext()).inflate(R.layout.layout_note_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mdl_note mdl=list.get(position);
        holder.tv_title.setText(mdl.getTitle());
        holder.tv_date.setText(mdl.getDate()+"-" + mdl.getTime());
       int color = Integer.parseInt(mdl.getColor());
        holder.cv_item.setCardBackgroundColor(color);

        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app_var.note=mdl;
                if(app_var.note.locked==1)
                {
                    dlg_unlock dlg=new dlg_unlock();
                    FragmentManager manager=((AppCompatActivity) ctx).getSupportFragmentManager();
                    dlg.show(manager,"del");
                }
                else
                {

                    database= FirebaseDatabase.getInstance();
                    ref = database.getReference("notes").child(String.valueOf(mdl.getId()));
                    ref.removeValue();
                }

            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app_var.note=mdl;
                if(app_var.note.locked==1)
                {
                    dlg_unlock dlg=new dlg_unlock();
                    FragmentManager manager=((AppCompatActivity) ctx).getSupportFragmentManager();
                    dlg.show(manager,"edit");
                }
                else
                {
                    ctx.startActivity(new Intent(ctx,atv_note_edit.class));
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app_var.note=mdl;

                if(app_var.note.locked==1)
                {
                    dlg_unlock dlg=new dlg_unlock();
                    FragmentManager manager=((AppCompatActivity) ctx).getSupportFragmentManager();
                    dlg.show(manager,"show");
                }
                else
                {
                    ctx.startActivity(new Intent(ctx,atv_note_read.class));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_title,tv_date;
        ImageButton btn_del,btn_edit;
        CardView cv_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title=itemView.findViewById(R.id.tv_title);
            tv_date=itemView.findViewById(R.id.tv_date);
            btn_del=itemView.findViewById(R.id.btn_del);
            btn_edit=itemView.findViewById(R.id.btn_edit);
            cv_item=itemView.findViewById(R.id.cv_item);


        }
    }
}
