package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link dlg_unlock#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dlg_unlock extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public dlg_unlock() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dlg_unlock.
     */
    // TODO: Rename and change types and number of parameters
    public static dlg_unlock newInstance(String param1, String param2) {
        dlg_unlock fragment = new dlg_unlock();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    FirebaseDatabase database;
    DatabaseReference ref;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.drw_corner_dlg);
        return inflater.inflate(R.layout.dlg_unlock, container, false);
    }

    Button btn_check;
    TextInputEditText et_password;
    @Override
    public void onStart() {
        super.onStart();

        et_password=getDialog().findViewById(R.id.et_password);
        btn_check=getDialog().findViewById(R.id.btn_check);

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_password.length()==0)
                {
                    Toast.makeText(getActivity(), "لطفا رمز را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                }

                check();
            }
        });
    }

    private void check()
    {
        String cmd=getTag();
        if(et_password.getText().toString().equals(app_var.note.getPassword()))
        {
            if(cmd.equals("show"))
            {
                getActivity().startActivity(new Intent(getActivity(),atv_note_read.class));
            }
            else if(cmd.equals("edit"))
            {
                getActivity().startActivity(new Intent(getActivity(),atv_note_edit.class));
            }

            else if(cmd.equals("del"))
            {

                database= FirebaseDatabase.getInstance();
                ref = database.getReference("notes").child(String.valueOf(app_var.note.getId()));
                ref.removeValue();
            }
        }
        else

        {
            Toast.makeText(getActivity(), "رمز عبور اشتباه می باشد", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}