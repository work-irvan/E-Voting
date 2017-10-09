package com.muhammad_irvan.evv.admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.muhammad_irvan.evv.admin.data.DataKandidatPenyelenggaraTerpilih;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UbahKandidat extends AppCompatActivity {
    TextView textNama, textSlogan, textTglLahir, textAlamat, textKeterangan;
    EditText[] editTexts = new EditText[5];
    ImageView image;
    Button simpan, hapus;
    private Bitmap bitmap;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_kandidat);

        toolbar = (Toolbar) findViewById(R.id.toolbarUbahKandidat);
        toolbar.setTitle("Merubah Kandidat");

        editTexts[0] = (EditText) findViewById(R.id.ubah_kandidat_penyelenggara_namaKandidat);
        editTexts[1] = (EditText)findViewById(R.id.ubah_kandidat_penyelenggara_slogan);
        editTexts[2] = (EditText)findViewById(R.id.ubah_kandidat_penyelenggara_tglLahir);
        editTexts[3] = (EditText)findViewById(R.id.ubah_kandidat_penyelenggara_alamat);
        editTexts[4] = (EditText)findViewById(R.id.ubah_kandidat_penyelenggara_keterangan);

        editTexts[0].setText(DataKandidatPenyelenggaraTerpilih.getKandidatNama());
        editTexts[1].setText(DataKandidatPenyelenggaraTerpilih.getKandidatSlogan());
        editTexts[2].setText(DataKandidatPenyelenggaraTerpilih.getKandidatTanggal());
        editTexts[3].setText(DataKandidatPenyelenggaraTerpilih.getKandidatAlamat());
        editTexts[4].setText(DataKandidatPenyelenggaraTerpilih.getKandidatKeterangan());


        image = (ImageView) findViewById(R.id.ubah_kandidat_penyelenggara_image);

        image.setImageBitmap(DataKandidatPenyelenggaraTerpilih.getKandidatImage());

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        bitmap = DataKandidatPenyelenggaraTerpilih.getKandidatImage();

        simpan = (Button)findViewById(R.id.ubah_kandidat_penyelenggara_simpan);
        hapus = (Button)findViewById(R.id.ubah_kandidat_penyelenggara_hapus);

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(UbahKandidat.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(UbahKandidat.this);
                builder.setTitle("Hapus Kandidat");
                builder.setMessage("Apakah anda yakin akan menghapus " + DataKandidatPenyelenggaraTerpilih.getKandidatNama() + " dari kandidat?");
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

        editTexts[2].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            private DatePickerDialog.OnDateSetListener mDatesetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String my_date = dayOfMonth + "-" + Integer.valueOf(monthOfYear + 1) + "-" + year;
                    editTexts[2].setText(my_date);
                }
            };
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Calendar calender = Calendar.getInstance();
                    Dialog dialogDate = new DatePickerDialog(UbahKandidat.this, mDatesetListener, calender.get(Calendar.YEAR),
                            calender.get(Calendar.MONTH), calender
                            .get(Calendar.DAY_OF_MONTH));
                    dialogDate.show();
                }
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validasi(editTexts)){
                    ubah();
                }
            }
        });

    }
    public void ubah(){
        final ProgressDialog loading = ProgressDialog.show(UbahKandidat.this,"Menyimpan Perubahan...","Tunggu Sebentar...",false,false);
        RequestQueue queue = Volley.newRequestQueue(UbahKandidat.this);
        final String URL = BuildConfig.BaseUrl + "api/updatekandidat";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    loading.dismiss();
                    Log.d("RESPONN", response);
                    JSONObject jObject = new JSONObject(response);
                    if(jObject.getBoolean("umpan")){
                        Toast.makeText(getApplicationContext(), "Perubahan Berhasi", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Perubahan Gagal", Toast.LENGTH_SHORT).show();
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
                String image = null;
                if(bitmap != null) {
                    image = getStringImage(bitmap);
                }else{
                    image = "";
                }

                map.put("id_kandidat", DataKandidatPenyelenggaraTerpilih.getKandidatId());
                map.put("id_pemilihan", DataKandidatPenyelenggaraTerpilih.getKandidatIdPemilihan());
                map.put("nama_kanidat",editTexts[0].getText().toString());
                map.put("slogan",editTexts[1].getText().toString());
                map.put("tgl_lahir",editTexts[2].getText().toString());
                map.put("alamat",editTexts[3].getText().toString());
                map.put("keterangan",editTexts[4].getText().toString());
                map.put("gambar",image);

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
    public void hapus(){
        final ProgressDialog loading = ProgressDialog.show(UbahKandidat.this,"Menghapus Kandidat...","Tunggu Sebentar...",false,false);
        RequestQueue queue = Volley.newRequestQueue(UbahKandidat.this);
        final String URL = BuildConfig.BaseUrl + "api/hapuskandidat";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    loading.dismiss();
                    Log.d("RESPONN", response);
                    JSONObject jObject = new JSONObject(response);
                    if(jObject.getBoolean("umpan")){
                        Toast.makeText(getApplicationContext(), "Perubahan Berhasi", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Perubahan Gagal", Toast.LENGTH_SHORT).show();
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
                map.put("id_kandidat", DataKandidatPenyelenggaraTerpilih.getKandidatId());
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
                image.setImageBitmap(bitmap);
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
    public Boolean validasi(EditText[] editTexts){
        Boolean tidakkosong = true;
        for (int i = 0 ; i < editTexts.length; i++){
            if(TextUtils.isEmpty(editTexts[i].getText())){
                editTexts[i].requestFocus();
                i = editTexts.length+1;
                tidakkosong = false;
            }
        }
        return tidakkosong;
    }
}
