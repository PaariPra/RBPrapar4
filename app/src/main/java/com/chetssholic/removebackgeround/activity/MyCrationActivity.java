package com.chetssholic.removebackgeround.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chetssholic.removebackgeround.R;
import com.chetssholic.removebackgeround.adapter.CreationAdapter;
import com.chetssholic.removebackgeround.adsclass.ShowNAtivrbannerAds;
import com.chetssholic.removebackgeround.interfaceces.selectectposion;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class MyCrationActivity extends AppCompatActivity {
  private ImageView ic_back2;
  private TextView iv_no;
  private RecyclerView rv_alldata;
  private CreationAdapter myListAdapter;
  private ArrayList<File> rm;
  private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cration);

        frameLayout=findViewById(R.id.frameLayout);


        ShowNAtivrbannerAds showNAtivrbannerAds = new ShowNAtivrbannerAds();
        showNAtivrbannerAds.refreshAd(MyCrationActivity.this, frameLayout);



        if (getIntent().getExtras().getString("type").equals("two")) {

            Uri uri = Uri.parse(getIntent().getExtras().getString("uri"));
            Intent intent = new Intent(MyCrationActivity.this, PreviewActivity.class);
            intent.putExtra("uri", uri.toString());



            startActivity(intent);


        }


        ic_back2 = findViewById(R.id.iv_back);
        rv_alldata = findViewById(R.id.rv_alldata);
        iv_no = findViewById(R.id.iv_no);





        ic_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();


        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+Environment.DIRECTORY_DOWNLOADS ,
                "HPhotoHollic");


        File[] pictures = file.listFiles();

        if(pictures!=null)
        {

            rm= new ArrayList<File>();



            if (pictures.length > 0) {

                for (int i = 0; i < pictures.length; i++) {
                    rm.add(pictures[i]);
                }

                iv_no.setVisibility(View.GONE);
                rv_alldata.setVisibility(View.VISIBLE);

                Collections.reverse(rm);
                myListAdapter = new CreationAdapter(rm, MyCrationActivity.this, new selectectposion() {
                    @Override
                    public void potinodate(int postion) {


                    }
                });
                GridLayoutManager gridLayoutManager = new GridLayoutManager(MyCrationActivity.this, 2);
                rv_alldata.setLayoutManager(gridLayoutManager);
                rv_alldata.setHasFixedSize(true);
                rv_alldata.setAdapter(myListAdapter);


            } else {
                iv_no.setVisibility(View.VISIBLE);
                rv_alldata.setVisibility(View.GONE);
            }
        }
        else
        {
            iv_no.setVisibility(View.VISIBLE);
            rv_alldata.setVisibility(View.GONE);

        }



    }
}