package com.muhammad_irvan.evv.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class TambahPanitia extends AppCompatActivity {
    EditText[] editTexts = new EditText[3];
    RadioButton[] radioButtons = new RadioButton[2];
    String level = "";
    ProgressBar progressBar;
    Button btnTambah;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_panitia);

        toolbar = (Toolbar) findViewById(R.id.toolbarTambahPanitia);
        toolbar.setTitle("Tambah Panitia");
        setSupportActionBar(toolbar);

        btnTambah = (Button)findViewById(R.id.tambahpanitia_btnTambah);
        editTexts[0] = (EditText)findViewById(R.id.tambahpanitia_nama);
        editTexts[1] = (EditText)findViewById(R.id.tambahpanitia_email);
        editTexts[2] = (EditText)findViewById(R.id.tambahpanitia_password);

        radioButtons[0] = (RadioButton)findViewById(R.id.tambahpanitia_radioKandidat);
        radioButtons[1] = (RadioButton)findViewById(R.id.tambahpanitia_radioPeserta);

        progressBar = (ProgressBar)findViewById(R.id.tambahpanitia_progerssbar1);

        radioButtons[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    level = "1";
                }
            }
        });

        radioButtons[1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    level = "2";
                }
            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validasi(editTexts)){
                    Tambah();
                }
            }
        });
    }


    public Boolean validasi(EditText[] input){
        Boolean lulus = true;
        for(int i = 0; i < input.length; i++){
            if(TextUtils.isEmpty(input[i].getText().toString())){
                input[i].setError("Tidak Boleh Kosong");
                input[i].requestFocus();
                return false;
            }
        }
        return lulus;
    }

    public void Tambah(){
        progressBar.setVisibility(View.VISIBLE);
        btnTambah.setEnabled(false);
        RequestQueue queue = Volley.newRequestQueue(TambahPanitia.this);
        final String URL = BuildConfig.BaseUrl + "api/tambahpanitia";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RESPONN", response);
                    JSONObject jObject = new JSONObject(response);
                    if(jObject.getBoolean("umpan")){
                        Toast.makeText(getApplicationContext(), "Panitia Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getApplicationContext(), MainPenyelenggara.class));
                        finish();
                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                        btnTambah.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Panitia Gagal Ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    btnTambah.setEnabled(true);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError){
                    Toast.makeText(getApplicationContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NoConnectionError){
                    Toast.makeText(getApplicationContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof AuthFailureError){
                    Toast.makeText(getApplicationContext(), "Auth Failure Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NetworkError){
                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof ServerError){
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof ParseError){
                    Toast.makeText(getApplicationContext(), "JSON Parse Error", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
                btnTambah.setEnabled(true);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new Hashtable<String,String>();

                if(radioButtons[0].isChecked()){
                    level = "1";
                }else if(radioButtons[1].isChecked()){
                    level = "2";
                }

                map.put("id_penyelenggara", InfoAkun.getInfo_penyelenggara_id_penyelenggara());
                map.put("nama",editTexts[0].getText().toString());
                map.put("level",level);
                map.put("email",editTexts[1].getText().toString());
                map.put("password",editTexts[2].getText().toString());

                return map;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                return headers;
            }
        };

        queue.add(request);
    }


}
