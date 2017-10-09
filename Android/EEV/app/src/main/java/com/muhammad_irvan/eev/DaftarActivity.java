package com.muhammad_irvan.eev;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DaftarActivity extends AppCompatActivity {
    EditText[] editTexts = new EditText[6];
    ProgressBar progressBar;
    Button btnDaftar;
    Toolbar toolbar;
    RadioButton rb1, rb2;
    String jenkel = "L";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        toolbar = (Toolbar) findViewById(R.id.toolbarDaftarPeserta);
        toolbar.setTitle("Daftar Peserta");
        setSupportActionBar(toolbar);

        editTexts[0] = (EditText)findViewById(R.id.daftar_peserta_noIdentitas);
        editTexts[1] = (EditText)findViewById(R.id.daftar_peserta_nama);
        editTexts[2] = (EditText)findViewById(R.id.daftar_peserta_tglLahir);
        editTexts[3] = (EditText)findViewById(R.id.daftar_peserta_alamat);
        editTexts[4] = (EditText)findViewById(R.id.daftar_peserta_email);
        editTexts[5] = (EditText)findViewById(R.id.daftar_peserta_password);

        rb1 = (RadioButton)findViewById(R.id.daftar_peserta_jenkelL);
        rb2 = (RadioButton)findViewById(R.id.daftar_peserta_jenkelP);

        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                jenkel = "L";
            }
        });
        rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                jenkel = "P";
            }
        });

        btnDaftar = (Button)findViewById(R.id.daftar_peserta_btnDaftar);

        progressBar = (ProgressBar)findViewById(R.id.daftar_peserta_progerssbar1);


        editTexts[2].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            private DatePickerDialog.OnDateSetListener mDatesetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String my_date = year+ "-" + Integer.valueOf(monthOfYear + 1) + "-" + dayOfMonth ;
                    editTexts[2].setText(my_date);
                }
            };
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Calendar calender = Calendar.getInstance();
                    Dialog dialogDate = new DatePickerDialog(DaftarActivity.this, mDatesetListener, calender.get(Calendar.YEAR),
                            calender.get(Calendar.MONTH), calender
                            .get(Calendar.DAY_OF_MONTH));
                    dialogDate.show();
                }
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validasi(editTexts)){
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
        RequestQueue queue = Volley.newRequestQueue(DaftarActivity.this);
        final String URL = BuildConfig.BaseUrl + "api/daftar";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RESPONN", response);
                    JSONObject jObject = new JSONObject(response);
                    if(jObject.getBoolean("umpan")){
                        Toast.makeText(getApplicationContext(), "Pendaftaran Berhasi", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                        btnDaftar.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Pendaftaran Gagal", Toast.LENGTH_SHORT).show();
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
                map.put("no_identitas",editTexts[0].getText().toString());
                map.put("nama",editTexts[1].getText().toString());
                map.put("jenis_kelamin",jenkel);
                map.put("tanggal_lahir",editTexts[2].getText().toString());
                map.put("alamat",editTexts[3].getText().toString());
                map.put("email",editTexts[4].getText().toString());
                map.put("password",editTexts[5].getText().toString());

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
