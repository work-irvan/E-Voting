package com.muhammad_irvan.evv.admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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
import com.muhammad_irvan.evv.admin.data.DataPanitia;
import com.muhammad_irvan.evv.admin.data.DataPanitiaTerpilih;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class UbahPanitia extends AppCompatActivity {
    EditText[] editTexts = new EditText[3];
    RadioButton[] radioButtons = new RadioButton[2];
    String level = "";
    ProgressBar progressBar;
    Button btnSimpan, btnHapus;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_panitia);

        toolbar = (Toolbar) findViewById(R.id.toolbarUbahPanitia);
        toolbar.setTitle("Merubah Panitia");

        editTexts[0] = (EditText)findViewById(R.id.ubahpanitia_nama);
        editTexts[1] = (EditText)findViewById(R.id.ubahpanitia_email);
        editTexts[2] = (EditText)findViewById(R.id.ubahpanitia_password);

        radioButtons[0] = (RadioButton)findViewById(R.id.ubahpanitia_radioKandidat);
        radioButtons[1] = (RadioButton)findViewById(R.id.ubahpanitia_radioPeserta);

        btnSimpan = (Button)findViewById(R.id.ubahpanitia_btnSimpan);
        btnHapus = (Button)findViewById(R.id.ubahpanitia_btnHapus);

        try{
            editTexts[0].setText(DataPanitiaTerpilih.getNama());
            editTexts[1].setText(DataPanitiaTerpilih.getEmail());
            editTexts[2].setText(DataPanitiaTerpilih.getPassword());

            Log.d("AAAAA", DataPanitiaTerpilih.getLevel());
            if(DataPanitiaTerpilih.getLevel() == "Panitia Kandidat"){
                radioButtons[0].setChecked(true);
                radioButtons[1].setChecked(false);
            }else{
                radioButtons[0].setChecked(false);
                radioButtons[1].setChecked(true);
            }
        }catch (Exception e){

        }

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

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validasi(editTexts)){
                    Rubah();
                }
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(UbahPanitia.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(UbahPanitia.this);
                builder.setTitle("Hapus Kandidat");
                builder.setMessage("Apakah anda yakin akan menghapus " + DataPanitiaTerpilih.getNama() + " dari kandidat?");
                builder.setPositiveButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hapus();
                    }
                });
                builder.show();
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

    public void Rubah() {
        final ProgressDialog loading = ProgressDialog.show(UbahPanitia.this,"Menyimpan Perubahan...","Tunggu Sebentar...",false,false);
        btnSimpan.setEnabled(false);
        RequestQueue queue = Volley.newRequestQueue(UbahPanitia.this);
        final String URL = BuildConfig.BaseUrl + "api/updatepanitia";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RESPONN", response);
                    JSONObject jObject = new JSONObject(response);
                    if (jObject.getBoolean("umpan")) {
                        Toast.makeText(getApplicationContext(), "Panitia Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getApplicationContext(), MainPenyelenggara.class));
                        finish();
                    } else {
                        loading.dismiss();
                        btnSimpan.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Panitia Gagal Ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    btnSimpan.setEnabled(true);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Auth Failure Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "JSON Parse Error", Toast.LENGTH_SHORT).show();
                }
                loading.dismiss();
                btnSimpan.setEnabled(true);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new Hashtable<String, String>();

                if (radioButtons[0].isChecked()) {
                    level = "1";
                } else if (radioButtons[1].isChecked()) {
                    level = "2";
                }


                map.put("id_panitia", DataPanitiaTerpilih.getId_panitia());
                map.put("nama", editTexts[0].getText().toString());
                map.put("level", level);
                map.put("email", editTexts[1].getText().toString());
                map.put("password", editTexts[2].getText().toString());
                Log.d("AAAA", editTexts[0].getText().toString());
                Log.d("AAAA", editTexts[1].getText().toString());
                Log.d("AAAA", editTexts[2].getText().toString());
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

    public void hapus(){
        final ProgressDialog loading = ProgressDialog.show(UbahPanitia.this,"Menghapus Panitia...","Tunggu Sebentar...",false,false);
        RequestQueue queue = Volley.newRequestQueue(UbahPanitia.this);
        final String URL = BuildConfig.BaseUrl + "api/hapuspanitia";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    loading.dismiss();
                    Log.d("RESPONN", response);
                    JSONObject jObject = new JSONObject(response);
                    if(jObject.getBoolean("umpan")){
                        Toast.makeText(getApplicationContext(), "Panitia Berhasil Terhapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Panitia Gagal Terhapus", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
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
                loading.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("id_panitia", DataPanitiaTerpilih.getId_panitia());
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
