package com.muhammad_irvan.eev;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.muhammad_irvan.eev.Adapter.AdapterListKandidat;
import com.muhammad_irvan.eev.Data.DataKandidat;
import com.muhammad_irvan.eev.Data.DataPemilihanTerpilih;
import com.muhammad_irvan.eev.Data.DataRespone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Irvan on 26/07/2017.
 */

public class Fragment_Kandidat extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public Fragment_Kandidat CustomListView = null;
    public ArrayList<DataKandidat> CustomListViewValuesArr = new ArrayList<DataKandidat>();
    ListView list;
    private SwipeRefreshLayout swipeRefreshLayout;
    AdapterListKandidat adapter;
    Button tambah;
    public Fragment_Kandidat() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_kandidat, container, false);

        Resources res = getResources();

        list=(ListView)rootView.findViewById(R.id.ListKandidat);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout_kandidat);

        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new AdapterListKandidat(getActivity(), CustomListViewValuesArr, res);
        list.setAdapter(adapter);
        list.setClickable(true);
        setList();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!CustomListViewValuesArr.isEmpty()){

                    final DataKandidat tempValues = (DataKandidat) CustomListViewValuesArr.get(position);

                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.detail_list_pemilihan_kandidat);
                    //dialog.setTitle("Opsi " + tempValues.getNama());

                    final ImageView detail_image_kanididat = (ImageView) dialog.findViewById(R.id.detail_list_pemilhan_kandidat_imagegambar);
                    final TextView detail_nama_kanididat = (TextView)dialog.findViewById(R.id.detail_list_pemilhan_kandidat_namakandidat);
                    final TextView detail_slogan_kanididat = (TextView)dialog.findViewById(R.id.detail_list_pemilhan_kandidat_slogan);
                    final TextView detail_tanggalLahir_kanididat = (TextView)dialog.findViewById(R.id.detail_list_pemilhan_kandidat_tanggallahir);
                    final TextView detail_keterangan_kanididat = (TextView)dialog.findViewById(R.id.detail_list_pemilhan_kandidat_keterangan);
                    final TextView detail_alamat_kanididat = (TextView)dialog.findViewById(R.id.detail_list_pemilhan_kandidat_alamat);
                    final Button btnPilih = (Button) dialog.findViewById(R.id.detail_list_pemilhan_kandidat_btnPilih);
                    final Switch btSwitch = (Switch) dialog.findViewById(R.id.detail_list_pemilhan_kandidat_switch);

                    detail_image_kanididat.setImageBitmap(tempValues.getKandidatImage());
                    detail_nama_kanididat.setText(tempValues.getKandidatNama());
                    detail_slogan_kanididat.setText(tempValues.getKandidatSlogan());
                    detail_tanggalLahir_kanididat.setText(tempValues.getKandidatTanggal());
                    detail_keterangan_kanididat.setText(tempValues.getKandidatKeterangan());
                    detail_alamat_kanididat.setText(tempValues.getKandidatAlamat());

                    btnPilih.setEnabled(false);
                    if(DataPemilihanTerpilih.getPenyelenggara_selesai().equals("0")){
                        btSwitch.setEnabled(true);
                    }else{
                        btSwitch.setEnabled(false);
                    }
                    btSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            btnPilih.setEnabled(isChecked);
                        }
                    });
                    btnPilih.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new enkripsi().execute(tempValues.getKandidatId());
                            dialog.hide();
                        }
                    });
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                    dialog.show();
                    dialog.getWindow().setAttributes(lp);
                }
            }
        });
        return rootView;

    }


    private void setList(){
        if(!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String URL = BuildConfig.BaseUrl + "api/tampilkandidatpenyelenggara";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RESPONN", response);
                    DataRespone.setRespone(response);
                    CustomListViewValuesArr.clear();
                    JSONObject jObject = new JSONObject(response);
                    JSONArray data_pemilihan = jObject.getJSONArray("umpan");
                    for(int i = 0; i < data_pemilihan.length(); i++){
                        JSONObject object = data_pemilihan.getJSONObject(i);
                        final DataKandidat sched = new DataKandidat();
                        sched.setKandidatId(object.getString("id_kandidat"));
                        sched.setKandidatIdPemilihan(object.getString("id_pemilihan"));
                        sched.setKandidatImage(ImageFromStr(object.getString("gambar")));
                        sched.setKandidatNama(object.getString("nama_kanidat"));
                        sched.setKandidatSlogan(object.getString("slogan"));
                        sched.setKandidatTanggal(object.getString("tgl_lahir"));
                        sched.setKandidatAlamat(object.getString("alamat"));
                        sched.setKandidatKeterangan(object.getString("keterangan"));

                        CustomListViewValuesArr.add(sched);
                    }
                    adapter.notifyDataSetChanged();
                    list.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    swipeRefreshLayout.setRefreshing(false);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
                swipeRefreshLayout.setRefreshing(false);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String,String>();
                map.put("id_pemilihan", DataPemilihanTerpilih.getPenyelenggara_idPemilihan());

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
    private void tambahsuara(final String chiper){
        if(!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String URL = BuildConfig.BaseUrl + "api/masukansuara";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RESPONN", response);
                    CustomListViewValuesArr.clear();
                    JSONObject jObject = new JSONObject(response);
                    if(jObject.getBoolean("umpan")){
                        Toast.makeText(getContext(), "Engkripsi Berhasil, Suara Berhasil Dikirim", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Pemilihan Gagal Dibuat", Toast.LENGTH_SHORT).show();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    swipeRefreshLayout.setRefreshing(false);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
                swipeRefreshLayout.setRefreshing(false);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String,String>();
                map.put("id_pemilihan", DataPemilihanTerpilih.getPenyelenggara_idPemilihan());
                map.put("id_peserta", InfoAkun.getInfo_peserta_id_peserta());
                map.put("suara", chiper);

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

    @Override
    public void onRefresh() {
        setList();
    }

    private class enkripsi extends AsyncTask<String, Void, String>{
        final ProgressDialog loading = ProgressDialog.show(getContext(),"Melakukan Autentikasi...","Tunggu Sebentar...",false,false);
        @Override
        protected String doInBackground(String... params) {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String tanggal = dateFormat.format(date);
            //String PlainText = tanggal + "." + params[0];
            String PlainText = "2017-08-23" + "." + params[0];
            String C = "";
            String Chiper = "";

            BigInteger temp1, temp2, mod, Ci;
            for(int i = 0; i<PlainText.length(); i++){

                int karakter = (int)PlainText.charAt(i);
                mod = new BigInteger(BuildConfig.PublicMod);
                Ci = new BigInteger(String.valueOf(karakter));
                temp1 = Ci.pow(Integer.valueOf(BuildConfig.PublicKey));
                temp2 = temp1.mod(mod);
                if(temp2.compareTo(new BigInteger("10")) < 0){
                    C = String.valueOf("00"+temp2);
                }else if(temp2.compareTo(new BigInteger("100")) < 0){
                    C = String.valueOf("0"+temp2);
                }else{
                    C = String.valueOf(temp2);
                }
                Chiper = Chiper + C;
                Log.d("Char", String.valueOf(PlainText.charAt(i) + " : " + Ci + " ^ " + BuildConfig.PublicKey + " = " + temp1 + " mod " + mod + " = " + temp2 + "(" + C + ")"));
            }
            Log.d("Char", "ENKRIPSI : " + Chiper);
            return Chiper;
        }


        @Override
        protected void onPostExecute(String s) {
            loading.dismiss();
            tambahsuara(s);
            super.onPostExecute(s);
        }
    }
}
