package com.muhammad_irvan.evv.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import java.util.Map;

public class DaftarPenyelenggara extends AppCompatActivity {

    EditText[] etDaftar = new EditText[6];
    Button btnDaftar;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_penyelenggara);

        etDaftar[0] = (EditText)findViewById(R.id.daftar_penyelenggara_noIdentitas);
        etDaftar[1] = (EditText)findViewById(R.id.daftar_penyelenggara_namaOrganisasi);
        etDaftar[2] = (EditText)findViewById(R.id.daftar_penyelenggara_nama);
        etDaftar[3] = (EditText)findViewById(R.id.daftar_penyelenggara_alamat);
        etDaftar[4] = (EditText)findViewById(R.id.daftar_penyelenggara_email);
        etDaftar[5] = (EditText)findViewById(R.id.daftar_penyelenggara_password);

        btnDaftar = (Button)findViewById(R.id.daftar_penyelenggara_daftar);

        progressBar = (ProgressBar)findViewById(R.id.daftar_penyelenggara_progressbar1);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validasi(etDaftar)){
                    daftar();
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
            }else{
                if(i==4){
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

    public void daftar(){
        progressBar.setVisibility(View.VISIBLE);
        btnDaftar.setEnabled(false);
        RequestQueue queue = Volley.newRequestQueue(DaftarPenyelenggara.this);
        final String URL = BuildConfig.BaseUrl + "api/daftarpenyelenggara";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RESPONN", response);
                    JSONObject jObject = new JSONObject(response);
                    if(jObject.getBoolean("umpan")){
                        Toast.makeText(getApplicationContext(), "Pendaftaran Berhasi;", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                        btnDaftar.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Pendaftaran Gagal;", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    btnDaftar.setEnabled(true);
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
                btnDaftar.setEnabled(true);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("no_identitas",etDaftar[0].getText().toString());
                map.put("nama_organisasi",etDaftar[1].getText().toString());
                map.put("nama_penyelenggara",etDaftar[2].getText().toString());
                map.put("alamat",etDaftar[3].getText().toString());
                map.put("email",etDaftar[4].getText().toString());
                map.put("password",etDaftar[5].getText().toString());

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
