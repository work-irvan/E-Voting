package com.muhammad_irvan.eev;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.appcompat.*;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MasukActivity extends AppCompatActivity {
    private Toolbar toolbar;
    EditText[] editTexts = new EditText[2];
    TextView linkDaftar;
    Button btnMasuk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        toolbar.setTitle("Masuk Aplikasi");
        setSupportActionBar(toolbar);

        editTexts[0] = (EditText)findViewById(R.id.masuk_input_email);
        editTexts[1] = (EditText)findViewById(R.id.masuk_input_password);

        linkDaftar = (TextView)findViewById(R.id.masuk_link_signup);
        btnMasuk = (Button)findViewById(R.id.masuk_btn_login);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validasi(editTexts)){
                    masuk();
                    //startActivity(new Intent(MasukActivity.this, MainActivityPeserta.class));
                }
            }
        });

        linkDaftar = (TextView)findViewById(R.id.masuk_link_signup);

        linkDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MasukActivity.this, DaftarActivity.class));
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
            }else{
                if(i==0){
                    if(!Patterns.EMAIL_ADDRESS.matcher(input[i].getText().toString()).matches()){
                        input[i].setError("Format Email Tidak Valid");
                        input[i].requestFocus();
                        lulus = false;
                    }
                }
            }
        }
        return lulus;
    }

    public void masuk(){
        btnMasuk.setEnabled(false);
        RequestQueue queue = Volley.newRequestQueue(MasukActivity.this);
        final ProgressDialog loading = ProgressDialog.show(MasukActivity.this,"Melakukan Autentikasi...","Tunggu Sebentar...",false,false);
        final String URL = BuildConfig.BaseUrl + "api/masuk";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RESPONN", response);
                    JSONObject jObject = new JSONObject(response);

                    if(jObject.getBoolean("umpan")){
                        JSONArray info_akun = jObject.getJSONArray("info_akun");
                        JSONObject object = info_akun.getJSONObject(0);

                        InfoAkun.setInfo_peserta_id_peserta(object.getString("id_peserta"));
                        InfoAkun.setInfo_peserta_no_identitas(object.getString("no_identitas"));
                        InfoAkun.setInfo_peserta_nama(object.getString("nama"));
                        InfoAkun.setInfo_peserta_jenis_kelamin(object.getString("jenis_kelamin"));
                        InfoAkun.setInfo_peserta_tanggal_lahir(object.getString("tanggal_lahir"));
                        InfoAkun.setInfo_peserta_alamat(object.getString("alamat"));
                        InfoAkun.setInfo_peserta_email(object.getString("email"));
                        startActivity(new Intent(MasukActivity.this, MainActivityPeserta.class));
                        finish();
                    }else{
                        btnMasuk.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Login Gagal : " + jObject.getString("keterangan"), Toast.LENGTH_SHORT).show();
                    }
                    loading.dismiss();
                } catch (JSONException e) {
                    btnMasuk.setEnabled(true);
                    loading.dismiss();
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
                btnMasuk.setEnabled(true);
                loading.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("email",editTexts[0].getText().toString());
                map.put("password",editTexts[1].getText().toString());
                return map;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("USER", "2");
                return headers;
            }
        };
        queue.add(request);
    }
}
