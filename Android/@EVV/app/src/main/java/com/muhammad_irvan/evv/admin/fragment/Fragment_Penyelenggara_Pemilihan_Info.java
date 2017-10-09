package com.muhammad_irvan.evv.admin.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.muhammad_irvan.evv.admin.BuildConfig;
import com.muhammad_irvan.evv.admin.InfoAkun;
import com.muhammad_irvan.evv.admin.MainPenyelenggaraActivity;
import com.muhammad_irvan.evv.admin.PesertaActivity;
import com.muhammad_irvan.evv.admin.R;
import com.muhammad_irvan.evv.admin.UbahPemilihan;
import com.muhammad_irvan.evv.admin.data.DataPemilihanPenyelenggaraTerpilih;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Irvan on 26/07/2017.
 */

public class Fragment_Penyelenggara_Pemilihan_Info extends Fragment {
    public Fragment_Penyelenggara_Pemilihan_Info() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    TextView[] textViews = new TextView[6];
    Button btnRincianPeserta, btnHapusPublish, btnUbah;
    ImageView image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_pemilihan_penyelenggara_info, container, false);
        textViews[0] = (TextView)rootView.findViewById(R.id.penyelenggara_pemilihan_kode);
        textViews[1] = (TextView)rootView.findViewById(R.id.penyelenggara_pemilihan_namaPemilihan);
        textViews[2] = (TextView)rootView.findViewById(R.id.penyelenggara_pemilihan_tglMaxGabung);
        textViews[3] = (TextView)rootView.findViewById(R.id.penyelenggara_pemilihan_tglMaxVote);
        textViews[4] = (TextView)rootView.findViewById(R.id.penyelenggara_pemilihan_tglPerhitungan);
        textViews[5] = (TextView)rootView.findViewById(R.id.penyelenggara_pemilihan_keterangan);

        btnRincianPeserta = (Button)rootView.findViewById(R.id.penyelenggara_pemilihan_btnRincian);

        btnRincianPeserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PesertaActivity.class));
            }
        });

        if(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_simpanStatus().equals("Draft")){
            btnRincianPeserta.setEnabled(false);
        }else{
            btnRincianPeserta.setEnabled(true);
        }




        btnHapusPublish = (Button)rootView.findViewById(R.id.penyelenggara_pemilihan_btnPublish);
        btnUbah = (Button)rootView.findViewById(R.id.penyelenggara_pemilihan_btnUbah);

        if(InfoAkun.getLevel() == 0){
            btnHapusPublish.setEnabled(true);
            btnUbah.setEnabled(true);
        }else if(InfoAkun.getLevel() == 1){
            btnHapusPublish.setEnabled(false);
            btnUbah.setEnabled(false);
        }else if(InfoAkun.getLevel() == 2){
            btnHapusPublish.setEnabled(false);
            btnUbah.setEnabled(false);
        }

        image = (ImageView)rootView.findViewById(R.id.penyelenggara_pemilihan_image);
        image.setImageBitmap(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_image());
        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UbahPemilihan.class));
            }
        });
        btnHapusPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnHapusPublish.getText().equals("Publish")){
                    publish();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Hapus Pemilihan ");
                    builder.setCancelable(false);
                    builder.setMessage("Apa anda yakin akan menghapus " + DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_namaPemilihan());
                    builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            hapus();
                        }
                    });
                    builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });


        if(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_simpanStatus().equals("Draft")){
            btnHapusPublish.setText("Publish");
            btnUbah.setVisibility(View.VISIBLE);
        }else{
            btnHapusPublish.setText("Hapus");
            btnUbah.setVisibility(View.GONE);
        }

        textViews[0].setText(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_idPemilihan());
        textViews[1].setText(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_namaPemilihan());
        textViews[2].setText(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_tanggalMaxGabung());
        textViews[3].setText(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_tanggalMaxVote());
        textViews[4].setText(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_tanggalPerhitungan());
        textViews[5].setText(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_keterangan());


        return rootView;
    }
    public void hapus(){
        final ProgressDialog loading = ProgressDialog.show(getContext(),"Hapus Pemilihan...","Tunggu Sebentar...",false,false);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final String URL = BuildConfig.BaseUrl + "api/hapuspemilihan";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                JSONObject jObject = null;
                try {
                    jObject = new JSONObject(response);
                    if(jObject.getBoolean("umpan")){
                        startActivity(new Intent(getContext(), MainPenyelenggaraActivity.class));
                        getActivity().finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError){
                    Toast.makeText(getContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NoConnectionError){
                    Toast.makeText(getContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof AuthFailureError){
                    Toast.makeText(getContext(), "Auth Failure Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NetworkError){
                    Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof ServerError){
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof ParseError){
                    Toast.makeText(getContext(), "JSON Parse Error", Toast.LENGTH_SHORT).show();
                }
                loading.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("id_pemilihan",DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_idPemilihan());

                return map;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("APIKEY", "indonesian");
                return headers;
            }
        };
        queue.add(request);
    }

    public void publish(){
        final ProgressDialog loading = ProgressDialog.show(getContext(),"Hapus Pemilihan...","Tunggu Sebentar...",false,false);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final String URL = BuildConfig.BaseUrl + "api/updatestatuspublish";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                JSONObject jObject = null;
                try {
                    jObject = new JSONObject(response);
                    if(jObject.getBoolean("umpan")){
                        startActivity(new Intent(getContext(), MainPenyelenggaraActivity.class));
                        getActivity().finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError){
                    Toast.makeText(getContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NoConnectionError){
                    Toast.makeText(getContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof AuthFailureError){
                    Toast.makeText(getContext(), "Auth Failure Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NetworkError){
                    Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof ServerError){
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof ParseError){
                    Toast.makeText(getContext(), "JSON Parse Error", Toast.LENGTH_SHORT).show();
                }
                loading.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("status","1");
                map.put("id_pemilihan",DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_idPemilihan());

                return map;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("APIKEY", "indonesian");
                return headers;
            }
        };
        queue.add(request);
    }
}
