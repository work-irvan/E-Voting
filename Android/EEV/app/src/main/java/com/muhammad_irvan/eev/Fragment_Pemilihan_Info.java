package com.muhammad_irvan.eev;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.muhammad_irvan.eev.Data.DataPemilihanTerpilih;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Irvan on 26/07/2017.
 */

public class Fragment_Pemilihan_Info extends Fragment{
    public Fragment_Pemilihan_Info() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    TextView[] textViews = new TextView[6];
    Button btnRincianPeserta;
    ImageView image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_pemilihan_info, container, false);
        textViews[0] = (TextView)rootView.findViewById(R.id.penyelenggara_pemilihan_kode);
        textViews[1] = (TextView)rootView.findViewById(R.id.penyelenggara_pemilihan_namaPemilihan);
        textViews[2] = (TextView)rootView.findViewById(R.id.pemilihan_tglMaxGabung);
        textViews[3] = (TextView)rootView.findViewById(R.id.pemilihan_tglMaxVote);
        textViews[4] = (TextView)rootView.findViewById(R.id.pemilihan_tglPerhitungan);
        textViews[5] = (TextView)rootView.findViewById(R.id.penyelenggara_pemilihan_keterangan);




        image = (ImageView)rootView.findViewById(R.id.penyelenggara_pemilihan_image);
        image.setImageBitmap(DataPemilihanTerpilih.getPenyelenggara_image());





        textViews[0].setText(DataPemilihanTerpilih.getPenyelenggara_idPemilihan());
        textViews[1].setText(DataPemilihanTerpilih.getPenyelenggara_namaPemilihan());
        textViews[2].setText(DataPemilihanTerpilih.getPenyelenggara_tanggalMaxGabung());
        textViews[3].setText(DataPemilihanTerpilih.getPenyelenggara_tanggalMaxVote());
        textViews[4].setText(DataPemilihanTerpilih.getPenyelenggara_tanggalPerhitungan());
        textViews[5].setText(DataPemilihanTerpilih.getPenyelenggara_keterangan());


        return rootView;
    }



}
