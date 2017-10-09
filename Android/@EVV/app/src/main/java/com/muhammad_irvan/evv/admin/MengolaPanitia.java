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

import com.muhammad_irvan.evv.admin.fragment.Fragement_List_Panitia;

import java.util.ArrayList;
import java.util.List;

public class MengolaPanitia extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mengola_panitia);

        toolbar = (Toolbar) findViewById(R.id.toolbarMengelolaPanitia);
        toolbar.setTitle(InfoAkun.getInfo_penyelenggara_nama_organisasi());
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpagerMengelolaPanitia);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabsMengelolaPanitia);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        MengolaPanitia.ViewPagerAdapter adapter = new MengolaPanitia.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Fragement_List_Panitia(), "PANITIA");
        //adapter.addFrag(, "TAMBAH");

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
        getMenuInflater().inflate(R.menu.menu_panitia, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.navbar_tambah:
                startActivity(new Intent(MengolaPanitia.this, TambahPanitia.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
