package com.muhammad_irvan.evv.admin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.muhammad_irvan.evv.admin.MengolaPanitia;
import com.muhammad_irvan.evv.admin.R;

/**
 * Created by Irvan on 24/07/2017.
 */

public class Fragment_Pengaturan_Pemilihan extends Fragment {

    public Fragment_Pengaturan_Pemilihan() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    Button btnMengelolaPanitia;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        btnMengelolaPanitia = (Button)rootView.findViewById(R.id.panitia_btnMengelola);

        btnMengelolaPanitia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MengolaPanitia.class));
            }
        });
        return rootView;
    }
}
