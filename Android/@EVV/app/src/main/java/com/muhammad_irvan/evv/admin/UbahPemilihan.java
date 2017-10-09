package com.muhammad_irvan.evv.admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
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
import com.muhammad_irvan.evv.admin.data.DataPemilihanPenyelenggaraTerpilih;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



public class UbahPemilihan extends AppCompatActivity {
    EditText[] editTexts = new EditText[5];
    ImageView image;
    Button btnUbah;
    private Toolbar toolbar;
    ImageView img;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_pemilihan);
        toolbar = (Toolbar) findViewById(R.id.toolbarUbahPemilihan);
        toolbar.setTitle("Merubah Pemilihan");

        editTexts[0] = (EditText)findViewById(R.id.ubah_pemilihan_namaPemilihan);
        editTexts[1] = (EditText)findViewById(R.id.ubah_pemilihan_keterangan);
        editTexts[2] = (EditText)findViewById(R.id.ubah_pemilihan_tglMaxGabung);
        editTexts[3] = (EditText)findViewById(R.id.ubah_pemilihan_tglMaxVote);
        editTexts[4] = (EditText)findViewById(R.id.ubah_pemilihan_tglPerhitungan);

        editTexts[0].setText(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_namaPemilihan());
        editTexts[1].setText(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_keterangan());
        editTexts[2].setText(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_tanggalMaxGabung());
        editTexts[3].setText(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_tanggalMaxVote());
        editTexts[4].setText(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_tanggalMaxGabung());

        img = (ImageView)findViewById(R.id.ubah_pemilihan_image);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        btnUbah = (Button)findViewById(R.id.ubah_pemilihan_btnUbah);
        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validasi(editTexts)){
                    ubah();
                }
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
                    Dialog dialogDate = new DatePickerDialog(UbahPemilihan.this, mDatesetListener, calender.get(Calendar.YEAR),
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
                    String my_date = dayOfMonth + "-" + Integer.valueOf(monthOfYear + 1) + "-" + year;
                    editTexts[3].setText(my_date);
                }
            };
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Calendar calender = Calendar.getInstance();
                    Dialog dialogDate = new DatePickerDialog(UbahPemilihan.this, mDatesetListener, calender.get(Calendar.YEAR),
                            calender.get(Calendar.MONTH), calender
                            .get(Calendar.DAY_OF_MONTH));
                    dialogDate.show();
                }
            }
        });
        editTexts[4].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            private DatePickerDialog.OnDateSetListener mDatesetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String my_date = dayOfMonth + "-" + Integer.valueOf(monthOfYear + 1) + "-" + year;
                    editTexts[4].setText(my_date);
                }
            };
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Calendar calender = Calendar.getInstance();
                    Dialog dialogDate = new DatePickerDialog(UbahPemilihan.this, mDatesetListener, calender.get(Calendar.YEAR),
                            calender.get(Calendar.MONTH), calender
                            .get(Calendar.DAY_OF_MONTH));
                    dialogDate.show();
                }
            }
        });
    }

    public void ubah(){
        final ProgressDialog loading = ProgressDialog.show(UbahPemilihan.this,"Mengambil Data...","Tunggu Sebentar...",false,false);
        RequestQueue queue = Volley.newRequestQueue(UbahPemilihan.this);
        final String URL = BuildConfig.BaseUrl + "api/updatepemilihan";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    loading.dismiss();
                    Log.d("RESPONN", response);
                    JSONObject jObject = new JSONObject(response);
                    if(jObject.getBoolean("umpan")){
                        Toast.makeText(getApplicationContext(), "Perubahan Berhasi;", Toast.LENGTH_SHORT).show();
                    }else{
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Perubahan Gagal;", Toast.LENGTH_SHORT).show();
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

                map.put("id_pemilihan",     DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_idPemilihan());
                map.put("nama_pemilihan",   editTexts[0].getText().toString());
                map.put("keterangan",       editTexts[1].getText().toString());
                map.put("max_gabung",       editTexts[2].getText().toString());
                map.put("max_vote",         editTexts[3].getText().toString());
                map.put("perhitungan",      editTexts[4].getText().toString());
                map.put("icon",image);

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
}
