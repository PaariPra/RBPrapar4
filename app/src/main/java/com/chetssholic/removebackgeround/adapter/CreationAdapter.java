package com.chetssholic.removebackgeround.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.chetssholic.removebackgeround.R;
import com.chetssholic.removebackgeround.activity.PreviewActivity;
import com.chetssholic.removebackgeround.interfaceces.selectectposion;

import java.io.File;
import java.util.ArrayList;


public class CreationAdapter extends RecyclerView.Adapter<CreationAdapter.ViewHolder>{
    private ArrayList<File> listdata;
    private Activity activity;
   private com.chetssholic.removebackgeround.interfaceces.selectectposion selectectposion;

   // RecyclerView recyclerView;  
    public CreationAdapter(ArrayList<File> listdata, Activity activity, selectectposion selectectposion) {
        this.listdata = listdata;
        this.activity = activity;
        this.selectectposion = selectectposion;
    }
    @Override  
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {  
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.image_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);  
        return viewHolder;  
    }  
  
    @Override  
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final File myListData = listdata.get(position);
        holder.imageView.setImageURI(Uri.parse(listdata.get(position).getAbsolutePath()));



        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View view) {




            activity.startActivity(new Intent(activity, PreviewActivity.class)
            .putExtra("uri",  FileProvider.getUriForFile(activity,
                    activity.getPackageName() + ".provider", listdata.get(position)).toString())
                            .putExtra("path", listdata.get(position).getAbsolutePath())
            );






            }  
        });







    }  
  
  
    @Override  
    public int getItemCount() {  
        return listdata.size();
    }  
  
    public static class ViewHolder extends RecyclerView.ViewHolder {  
        public ImageView imageView;  

        public ConstraintLayout relativeLayout;
        public ViewHolder(View itemView) {  
            super(itemView);  
            this.imageView =  itemView.findViewById(R.id.iv_show);
            relativeLayout = itemView.findViewById(R.id.iv_shaoee);
        }  
    }




}  