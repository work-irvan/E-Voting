package com.muhammad_irvan.evv.admin.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import com.muhammad_irvan.evv.admin.PemilihanPenyelenggara;
import com.muhammad_irvan.evv.admin.R;
import com.muhammad_irvan.evv.admin.adapter.AdapterListKandidatPenyelenggara;
import com.muhammad_irvan.evv.admin.data.DataKandidatPenyelenggara;
import com.muhammad_irvan.evv.admin.data.DataKandidatPenyelenggaraTerpilih;
import com.muhammad_irvan.evv.admin.data.DataPemilihanPenyelenggaraTerpilih;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Irvan on 05/08/2017.
 */

public class Fragment_Penyelenggara_Perhitungan extends Fragment {
    public Fragment_Penyelenggara_Perhitungan CustomListView = null;
    public ArrayList<DataKandidatPenyelenggara> CustomListViewValuesArr = new ArrayList<DataKandidatPenyelenggara>();
    ListView list;
    AdapterListKandidatPenyelenggara adapter;
    Button brhHitung;
    public Fragment_Penyelenggara_Perhitungan() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_penyelenggara_perhitungan, container, false);

        Resources res = getResources();

        list=(ListView)rootView.findViewById(R.id.ListPenyelenggaraPerhitungan);
        brhHitung = (Button)rootView.findViewById(R.id.perhitungan_btnHitung);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        try {
            Date date1  = new Date();
            Date date2  = sdf.parse(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_tanggalPerhitungan());
            if(date1.compareTo(date2) >= 0 && !DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_simpanStatus().equals("Draft")){
                brhHitung.setEnabled(true);
            }else{
                brhHitung.setEnabled(false);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        brhHitung.setEnabled(false);
        Log.d("AAAAAA", String.valueOf(DataPemilihanPenyelenggaraTerpilih.getSelesai()));

        if(DataPemilihanPenyelenggaraTerpilih.getSelesai()){
            brhHitung.setEnabled(false);
            ambildatahasilperhitungan();
        }else{
            if(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_simpanStatus().equals("Draft")){
                brhHitung.setEnabled(false);
            }else{
                brhHitung.setEnabled(true);
            }
        }


        brhHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ambildata();
                    setKandidat();
                    updatestatus();
                    brhHitung.setEnabled(false);
                    DataPemilihanPenyelenggaraTerpilih.setSelesai(true);
                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

        adapter = new AdapterListKandidatPenyelenggara(getActivity(), CustomListViewValuesArr, res);
        list.setAdapter(adapter);
        list.setClickable(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!CustomListViewValuesArr.isEmpty()){
                    final DataKandidatPenyelenggara tempValues = (DataKandidatPenyelenggara) CustomListViewValuesArr.get(position);


                }
            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        brhHitung.setEnabled(false);
        if(DataPemilihanPenyelenggaraTerpilih.getSelesai()){
            brhHitung.setEnabled(false);
            ambildatahasilperhitungan();
        }else{
            if(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_simpanStatus().equals("Draft")){
                brhHitung.setEnabled(false);
            }else{
                brhHitung.setEnabled(true);
            }
        }

    }

    public void setKandidat(){
        try {
            CustomListViewValuesArr.clear();
            JSONObject jObject = new JSONObject(DataKandidatPenyelenggaraTerpilih.getRespone());
            JSONArray data_pemilihan = jObject.getJSONArray("umpan");
            for(int i = 0; i < data_pemilihan.length(); i++){
                JSONObject object = data_pemilihan.getJSONObject(i);
                final DataKandidatPenyelenggara sched = new DataKandidatPenyelenggara();
                sched.setKandidatId(object.getString("id_kandidat"));
                sched.setKandidatNama(object.getString("nama_kanidat"));
                sched.setKandidatKeterangan("0");
                sched.setKandidatImage(ImageFromStr(object.getString("gambar")));

                CustomListViewValuesArr.add(sched);
            }
            adapter.notifyDataSetChanged();
            list.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void ambildata(){
        final ProgressDialog loading = ProgressDialog.show(getContext(),"Mengambil Data...","Tunggu Sebentar...",false,false);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String URL = BuildConfig.BaseUrl + "api/tampilperhitungan";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    loading.dismiss();
                    Log.d("RESPONN", response);
                    new deskripsi().execute(response);

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                brhHitung.setEnabled(true);
                if(error instanceof TimeoutError){
                    Toast.makeText(getActivity().getApplicationContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NoConnectionError){
                    Toast.makeText(getActivity().getApplicationContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof AuthFailureError){
                    Toast.makeText(getActivity().getApplicationContext(), "Auth Failure Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NetworkError){
                    Toast.makeText(getActivity().getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof ServerError){
                    Toast.makeText(getActivity().getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String,String>();
                map.put("id_pemilihan", DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_idPemilihan());

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

    public void ambildatahasilperhitungan(){
        final ProgressDialog loading = ProgressDialog.show(getContext(),"Mengambil Data...","Tunggu Sebentar...",false,false);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String URL = BuildConfig.BaseUrl + "api/tampilhasilperhitungan";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    loading.dismiss();
                    Log.d("RESPONN", response);
                    CustomListViewValuesArr.clear();
                    JSONObject jObject = new JSONObject(response);
                    JSONArray data_pemilihan = jObject.getJSONArray("umpan");
                    for(int i = 0; i < data_pemilihan.length(); i++) {
                        JSONObject object = data_pemilihan.getJSONObject(i);

                        final DataKandidatPenyelenggara sched = new DataKandidatPenyelenggara();
                        sched.setKandidatId(object.getString("id_kandidat"));
                        sched.setKandidatNama(object.getString("nama_kanidat"));
                        sched.setKandidatKeterangan(object.getString("perolehan_suara"));
                        sched.setKandidatImage(ImageFromStr(object.getString("gambar")));

                        CustomListViewValuesArr.add(sched);
                    }
                    adapter.notifyDataSetChanged();
                    list.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                brhHitung.setEnabled(true);
                if(error instanceof TimeoutError){
                    Toast.makeText(getActivity().getApplicationContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NoConnectionError){
                    Toast.makeText(getActivity().getApplicationContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof AuthFailureError){
                    Toast.makeText(getActivity().getApplicationContext(), "Auth Failure Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NetworkError){
                    Toast.makeText(getActivity().getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof ServerError){
                    Toast.makeText(getActivity().getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String,String>();
                map.put("id_pemilihan", DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_idPemilihan());

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

    public void updatestatus(){
        final ProgressDialog loading = ProgressDialog.show(getContext(),"Mengambil Data...","Tunggu Sebentar...",false,false);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String URL = BuildConfig.BaseUrl + "api/updaselsesaipemilihan";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    loading.dismiss();
                    Log.d("RESPONN", response);
                    new deskripsi().execute(response);

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                brhHitung.setEnabled(true);
                if(error instanceof TimeoutError){
                    Toast.makeText(getActivity().getApplicationContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NoConnectionError){
                    Toast.makeText(getActivity().getApplicationContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof AuthFailureError){
                    Toast.makeText(getActivity().getApplicationContext(), "Auth Failure Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NetworkError){
                    Toast.makeText(getActivity().getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof ServerError){
                    Toast.makeText(getActivity().getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String,String>();
                map.put("id_pemilihan", DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_idPemilihan());

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

    public Bitmap ImageFromStr(String str){
        Bitmap bmp;
        byte[] decodeImage = Base64.decode(str, Base64.DEFAULT);
        bmp = BitmapFactory.decodeByteArray(decodeImage, 0, decodeImage.length);
        return bmp;
    }

    public void update(){

        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
        DataPemilihanPenyelenggaraTerpilih.setSelesai(false);
        for(int n = 0; n < CustomListViewValuesArr.size(); n++){
            final DataKandidatPenyelenggara tempValues = (DataKandidatPenyelenggara) CustomListViewValuesArr.get(n);

            Tambah(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_idPemilihan(), tempValues.getKandidatId(), tempValues.getKandidatKeterangan());
        }
    }

    public void Tambah(final String idpemilihan, final String idkandidat, final String jumlah){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String URL = BuildConfig.BaseUrl + "api/simpanhasilperhitungan";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RESPONN", response);

                    JSONObject jObject = new JSONObject(response);

                    if(jObject.getBoolean("umpan")){
                        //Toast.makeText(getContext(), "Perhitungan Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getApplicationContext(), MainPenyelenggara.class));
                    }else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                brhHitung.setEnabled(true);
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
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new Hashtable<String,String>();

                map.put("id_pemilihan", idpemilihan);
                map.put("id_kandidat", idkandidat);
                map.put("perolehan_suara", jumlah);


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


    private class deskripsi extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            JSONObject jObject = null;
            try {
                jObject = new JSONObject(params[0]);
                JSONArray data_pemilihan = jObject.getJSONArray("umpan");
                if(data_pemilihan != null){
                    for(int i = 0; i < data_pemilihan.length(); i++){
                        JSONObject object = data_pemilihan.getJSONObject(i);
                        String Suara = object.getString("suara");

                        if(Suara.length() > 0){
                            String C = "";
                            int panjang = Suara.length();
                            int jumlah = (int)panjang/3;
                            String[] Ci = new String[jumlah];
                            if(panjang % 3 == 0){
                                int a = 0;
                                for(int n = 0; n<=panjang-3; n+=3){

                                    Ci[a] = Suara.substring(n, n+3);
                                    a++;
                                    Log.d("Chiper", String.valueOf(C));
                                }

                                BigInteger temp0, temp1, temp2, mod;
                                String PlainText = "";
                                for(int z = 0; z<Ci.length; z++){
                                    mod = new BigInteger(BuildConfig.PublicMod);
                                    temp0 = new BigInteger(Ci[z]);
                                    temp1 = temp0.pow(Integer.valueOf("493"));
                                    temp2 = temp1.mod(mod);
                                    PlainText = PlainText + Character.toString((char)temp2.intValue());
                                    Log.d("PlainText", String.valueOf(PlainText));
                                }

                                String[] hasil_suara = PlainText.split(Pattern.quote("."));
                                for(int n = 0; n < CustomListViewValuesArr.size(); n++){
                                    final DataKandidatPenyelenggara tempValues = (DataKandidatPenyelenggara) CustomListViewValuesArr.get(n);
                                    if(hasil_suara[1].equals(tempValues.getKandidatId())){
                                        int m = Integer.valueOf(tempValues.getKandidatKeterangan());
                                        m++;
                                        tempValues.setKandidatKeterangan(String.valueOf(m));
                                    }
                                    //Tambah(tempValues.getKandidatIdPemilihan(), tempValues.getKandidatId(), tempValues.getKandidatKeterangan());
                                }

                                return true;
                            }
                        }


                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                update();
            }
            super.onPostExecute(aBoolean);
        }
    }
}
