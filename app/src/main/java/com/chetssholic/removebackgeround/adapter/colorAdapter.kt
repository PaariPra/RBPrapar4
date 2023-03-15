package com.chetssholic.removebackgeround.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.chetssholic.removebackgeround.R
import com.chetssholic.removebackgeround.interfaceces.selectectposion2
import java.util.ArrayList

class colorAdapter(
    private val listdata: ArrayList<Dtalist>,
    private val activity: Activity,
    private val selectectposion: selectectposion2
) : RecyclerView.Adapter<colorAdapter.ViewHolder>() {
    private var select = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.color_row, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView")
    position: Int) {

        if(select==position)
        {
            holder.cv_change.backgroundTintList= ColorStateList.valueOf(activity.resources.getColor(R.color.purple_200))
        }
        else
        {
            holder.cv_change.backgroundTintList= ColorStateList.valueOf(activity.resources.getColor(R.color.white))

        }
        holder.imageView.setImageResource(listdata[position].posin1)
        holder.iv_two.setImageResource(listdata[position].posin2)




        holder.cv_change.setOnClickListener {

            select = position
            notifyDataSetChanged()
            selectectposion.potinodate(listdata[position].posin1,listdata[position].posin2)

        }
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    class ViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var relativeLayout: ConstraintLayout
        var cv_change: CardView
        var iv_two: ImageView

        init {
            imageView = itemView.findViewById<View>(R.id.imageView) as ImageView
            iv_two = itemView.findViewById<View>(R.id.iv_two) as ImageView
            relativeLayout = itemView.findViewById<View>(R.id.relativeLayout) as ConstraintLayout
            cv_change = itemView.findViewById<View>(R.id.cv_change) as CardView
        }
    }
}