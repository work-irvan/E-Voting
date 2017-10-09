package com.muhammad_irvan.evv.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    EditText[] editTexts = new EditText[2];
    Button btnMasuk, btnDaftarPeserta, btnDaftarPenyelenggara;
    ProgressBar progressBar;
    TextView[] textViews = new TextView[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        editTexts[0] = (EditText)findViewById(R.id.loginEmail);
        editTexts[1] = (EditText)findViewById(R.id.login_password);

        textViews[0] = (TextView)findViewById(R.id.login_tvPenyelenggara);
        textViews[1] = (TextView)findViewById(R.id.login_tvPanitia);

        textViews[0].setClickable(true);
        textViews[1].setClickable(true);

        textViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masuksebagai(true);
            }
        });

        textViews[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masuksebagai(false);
            }
        });


        btnMasuk = (Button)findViewById(R.id.login_btnMasuk);
        btnDaftarPenyelenggara = (Button)findViewById(R.id.login_btnDaftarPenyelenggara);

        progressBar = (ProgressBar)findViewById(R.id.login_progressbar1);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validasi(editTexts)){
                    masuk();
                }
            }
        });
        btnDaftarPenyelenggara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MasukActivity.this, DaftarPenyelenggara.class));
            }
        });
    }

    Boolean Penyelenggara = true;
    public void masuksebagai(Boolean nilai){
        this.Penyelenggara = nilai;
        if(nilai){
            textViews[0].setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            textViews[1].setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }else{
            textViews[1].setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            textViews[0].setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
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
        progressBar.setVisibility(View.VISIBLE);
        btnMasuk.setEnabled(false);
        RequestQueue queue = Volley.newRequestQueue(MasukActivity.this);

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

                        if(Penyelenggara == true){
                            InfoAkun.setInfo_penyelenggara_id_penyelenggara(object.getString("id_penyelenggara"));
                            InfoAkun.setInfo_penyelenggara_no_identitas(object.getString("no_identitas"));
                            InfoAkun.setInfo_penyelenggara_nama_organisasi(object.getString("nama_organisasi"));
                            InfoAkun.setInfo_penyelenggara_nama_penyelenggara(object.getString("nama_penyelenggara"));
                            InfoAkun.setInfo_penyelenggara_alamat(object.getString("alamat"));
                            InfoAkun.setInfo_penyelenggara_email(object.getString("email"));

                            InfoAkun.setLevel(0);
                            startActivity(new Intent(MasukActivity.this, MainPenyelenggaraActivity.class));
                        }else{
                            if(object.getString("level").equals("1")){
                                InfoAkun.setLevel(1);
                                InfoAkun.setInfo_penyelenggara_id_penyelenggara(object.getString("id_penyelenggara"));
                                InfoAkun.setInfo_penyelenggara_nama_organisasi(object.getString("nama_organisasi"));
                                startActivity(new Intent(MasukActivity.this, MainPenyelenggaraActivity.class));
                            }else if(object.getString("level").equals("2")){
                                InfoAkun.setLevel(2);
                                InfoAkun.setInfo_penyelenggara_id_penyelenggara(object.getString("id_penyelenggara"));
                                InfoAkun.setInfo_penyelenggara_nama_organisasi(object.getString("nama_organisasi"));
                                startActivity(new Intent(MasukActivity.this, MainPenyelenggaraActivity.class));
                            }
                        }
                        finish();
                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                        btnMasuk.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Login Gagal : " + jObject.getString("keterangan"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    btnMasuk.setEnabled(true);
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
                btnMasuk.setEnabled(true);
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

                if(Penyelenggara){
                    headers.put("USER", "0");
                }else{
                    headers.put("USER", "1");
                }

                return headers;
            }
        };
        queue.add(request);
    }
}
