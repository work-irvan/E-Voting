package com.muhammad_irvan.evv.admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static android.R.attr.bitmap;

public class TambahPemilihanPenyelenggara extends AppCompatActivity {
    EditText[] editTexts = new EditText[5];
    Button tambah;
    ProgressBar progressBar;
    ImageView img;
    private Bitmap bitmap;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pemilihan_penyelenggara);

        toolbar = (Toolbar) findViewById(R.id.toolbarTambahPemilihan);
        toolbar.setTitle("Tambah Pemilihan");
        setSupportActionBar(toolbar);

        editTexts[0] = (EditText)findViewById(R.id.tambah_pemilihan_namaPemilihan);
        editTexts[1] = (EditText)findViewById(R.id.tambah_pemilihan_tglMaxGabung);
        editTexts[2] = (EditText)findViewById(R.id.tambah_pemilihan_tglMaxVote);
        editTexts[3] = (EditText)findViewById(R.id.tambah_pemilihan_tglPerhitungan);
        editTexts[4] = (EditText)findViewById(R.id.tambah_pemilihan_keterangan);

        editTexts[1].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            private DatePickerDialog.OnDateSetListener mDatesetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String my_date = year + "-" + Integer.valueOf(monthOfYear + 1) + "-" + dayOfMonth ;
                    editTexts[1].setText(my_date);
                }
            };
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Calendar calender = Calendar.getInstance();
                    Dialog dialogDate = new DatePickerDialog(TambahPemilihanPenyelenggara.this, mDatesetListener, calender.get(Calendar.YEAR),
                            calender.get(Calendar.MONTH), calender
                            .get(Calendar.DAY_OF_MONTH));
                    dialogDate.show();
                }
            }
        });
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
                    Dialog dialogDate = new DatePickerDialog(TambahPemilihanPenyelenggara.this, mDatesetListener, calender.get(Calendar.YEAR),
                            calender.get(Calendar.MONTH), calender
                            .get(Calendar.DAY_OF_MONTH));
                    dialogDate.show();
                }
            }
        });
        editTexts[3].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            private DatePickerDialog.OnDateSetListener mDatesetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String my_date = year+ "-" + Integer.valueOf(monthOfYear + 1) + "-" + dayOfMonth ;
                    editTexts[3].setText(my_date);
                }
            };
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Calendar calender = Calendar.getInstance();
                    Dialog dialogDate = new DatePickerDialog(TambahPemilihanPenyelenggara.this, mDatesetListener, calender.get(Calendar.YEAR),
                            calender.get(Calendar.MONTH), calender
                            .get(Calendar.DAY_OF_MONTH));
                    dialogDate.show();
                }
            }
        });

        img = (ImageView)findViewById(R.id.tambah_pemilihan_image);

        progressBar = (ProgressBar)findViewById(R.id.tambah_pemilihan_progerssbar1);

        tambah = (Button)findViewById(R.id.tambah_pemilihan_btnTambah);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validasi(editTexts)){
                    Tambah();
                }
            }
        });
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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
        tambah.setEnabled(false);
        RequestQueue queue = Volley.newRequestQueue(TambahPemilihanPenyelenggara.this);
        final String URL = BuildConfig.BaseUrl + "api/tambahpemilihan";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RESPONN", response);
                    JSONObject jObject = new JSONObject(response);
                    if(jObject.getBoolean("umpan")){
                        Toast.makeText(getApplicationContext(), "Pemilihan Berhasi Dibuat", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainPenyelenggaraActivity.class));
                        finish();
                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                        tambah.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Pemilihan Gagal Dibuat", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    tambah.setEnabled(true);
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
                tambah.setEnabled(true);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new Hashtable<String,String>();
                String image = null;
                if(bitmap != null) {
                    image = getStringImage(bitmap);
                }else{
                    image = "";
                }



                map.put("id_penyelenggara",InfoAkun.getInfo_penyelenggara_id_penyelenggara());
                map.put("nama_pemilihan",editTexts[0].getText().toString());
                map.put("max_gabung",editTexts[1].getText().toString());
                map.put("max_vote",editTexts[2].getText().toString());
                map.put("perhitungan",editTexts[3].getText().toString());
                map.put("keterangan",editTexts[4].getText().toString());
                map.put("icon",image);

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
