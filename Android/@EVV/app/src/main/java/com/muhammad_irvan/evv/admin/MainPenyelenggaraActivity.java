package com.muhammad_irvan.evv.admin;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.muhammad_irvan.evv.admin.fragment.Fragment_Pengaturan_Pemilihan;
import com.muhammad_irvan.evv.admin.fragment.Fragment_Penyelenggara_Pemilihan;

import java.util.ArrayList;
import java.util.List;

public class MainPenyelenggaraActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_penyelenggara);

        toolbar = (Toolbar) findViewById(R.id.toolbarMainPenyelenggara);
        toolbar.setTitle(InfoAkun.getInfo_penyelenggara_nama_organisasi());
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpagerMainPenyelenggara);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabsMainPenyelenggara);
        tabLayout.setupWithViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager) {
        MainPenyelenggaraActivity.ViewPagerAdapter adapter = new MainPenyelenggaraActivity.ViewPagerAdapter(getSupportFragmentManager());
        if(InfoAkun.getLevel() == 0){
            adapter.addFrag(new Fragment_Penyelenggara_Pemilihan(), "PEMILIHAN");
            adapter.addFrag(new Fragment_Pengaturan_Pemilihan(), "PENGATURAN");
        }else if(InfoAkun.getLevel() == 1){
            adapter.addFrag(new Fragment_Penyelenggara_Pemilihan(), "PEMILIHAN");
        }else if(InfoAkun.getLevel() == 2){
            adapter.addFrag(new Fragment_Penyelenggara_Pemilihan(), "PEMILIHAN");
        }


        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_penyelenggara, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.penyelenggara_navTambahPemilihan:
                if(InfoAkun.getLevel() == 0){
                    startActivity(new Intent(MainPenyelenggaraActivity.this, TambahPemilihanPenyelenggara.class));
                }

                break;
            case R.id.penyelenggara_navLogout:
                Intent intent = new Intent(getApplicationContext(), MasukActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
