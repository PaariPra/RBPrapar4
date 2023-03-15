package com.chetssholic.removebackgeround.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PointF
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chetssholic.removebackgeround.R
import com.chetssholic.removebackgeround.adapter.Dtalist
import com.chetssholic.removebackgeround.adapter.MyListAdapter
import com.chetssholic.removebackgeround.adapter.colorAdapter
import com.chetssholic.removebackgeround.interfaceces.selectectposion
import com.chetssholic.removebackgeround.interfaceces.selectectposion2
import com.chetssholic.removebackgeround.utils.BitmapUtiles
import kotlinx.android.synthetic.main.activity_potrai.*
import kotlinx.android.synthetic.main.activity_potrai.fgView
import kotlinx.android.synthetic.main.activity_potrai.imageview_id
import kotlinx.android.synthetic.main.activity_potrai.imageview_id2
import kotlin.collections.ArrayList


class PotraiActivity : AppCompatActivity() {

    var bitmap: Bitmap? = null
    var myListAdapter: MyListAdapter? = null



    var d = 0f
    var newRot = 0f
    private var isZoomAndRotate = false
    private var isOutSide = false
    private val NONE = 0
    private val DRAG = 1
    private val ZOOM = 2
    private var mode = NONE
    private val start: PointF = PointF()
    private val mid: PointF = PointF()
    var oldDist = 1f
    private var xCoOrdinate = 0f
    private var yCoOrdinate: Float = 0f
    var lastEvent: FloatArray? = null

    private  var  data:Boolean= false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_potrai)


        initData();

        fgView.setColor(resources.getColor(R.color.purple_200),resources.getColor(R.color.purple_200))




        fgView.setval(true);
        imageview_id2.setOnTouchListener(null)


    }



    private fun initData() {


        val myListData: ArrayList<Int> = ArrayList()
        myListData.add(R.drawable.pot_1)
        myListData.add(R.drawable.por_2)
        myListData.add(R.drawable.pot_3)
        myListData.add(R.drawable.pot_4)
        myListData.add(R.drawable.pot_5)
        myListData.add(R.drawable.pot_6)
        myListData.add(R.drawable.pot_7)
        myListData.add(R.drawable.pot_8)



        val adapter = MyListAdapter(myListData, this@PotraiActivity, object : selectectposion {
            override fun potinodate(postion: Int) {
                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)

                var bt: Bitmap = BitmapUtiles.getBitmap(resources.getDrawable(postion))
                var bitmap2: Bitmap = Bitmap.createScaledBitmap(bt!!, displayMetrics.widthPixels, displayMetrics.widthPixels, false)
                fgView.setMask(bitmap2)


            }

        })
        rv_pot.setHasFixedSize(true)
        rv_pot.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rv_pot.adapter = adapter


        val myListData2: ArrayList<Dtalist> = ArrayList()
        myListData2.add(Dtalist(R.color.white,R.color.purple_200))
        myListData2.add(Dtalist(R.color.purple_200,R.color.white))

        myListData2.add(Dtalist(R.color.white,R.color.collage))
        myListData2.add(Dtalist(R.color.edit,R.color.white))
        myListData2.add(Dtalist(R.color.adjust,R.color.edit))
        myListData2.add(Dtalist(R.color.purple_200,R.color.adjust))
        myListData2.add(Dtalist(R.color.filter,R.color.filter))
        myListData2.add(Dtalist(R.color.filter,R.color.spiral))
        myListData2.add(Dtalist(R.color.adjust,R.color.collage))
        myListData2.add(Dtalist(R.color.collage,R.color.white))
        myListData2.add(Dtalist(R.color.purple_200,R.color.edit))




        myListData2.add( Dtalist(  R.color.white,R.color.col_1))
        myListData2.add( Dtalist(  R.color.white,R.color.col_1_s1))
        myListData2.add( Dtalist(  R.color.white,R.color.col_1_s3))
        myListData2.add( Dtalist(  R.color.white,R.color.col_2))
        myListData2.add( Dtalist(  R.color.white,R.color.col_2_s1))
        myListData2.add( Dtalist(  R.color.white,R.color.col_2_s3))
        myListData2.add( Dtalist(  R.color.white,R.color.col_3))
        myListData2.add( Dtalist(  R.color.white,R.color.col_3_s1))
        myListData2.add( Dtalist(  R.color.white,R.color.col_3_s3))
        myListData2.add( Dtalist(  R.color.white,R.color.col_4))
        myListData2.add( Dtalist(  R.color.white,R.color.col_4_s1))
        myListData2.add( Dtalist(  R.color.white,R.color.col_4_s3))
        myListData2.add( Dtalist(  R.color.white,R.color.col_5))
        myListData2.add( Dtalist(  R.color.white,R.color.col_5_s1))
        myListData2.add( Dtalist(  R.color.white,R.color.col_5_s3))
        myListData2.add( Dtalist(  R.color.white,R.color.col_6))
        myListData2.add( Dtalist(  R.color.white,R.color.col_6_s1))
        myListData2.add( Dtalist(  R.color.white,R.color.col_6_s3))
        myListData2.add( Dtalist(  R.color.white,R.color.col_7))
        myListData2.add( Dtalist(  R.color.white,R.color.col_7_s1))
        myListData2.add( Dtalist(  R.color.white,R.color.col_7_s3))


        val adapter2 = colorAdapter(myListData2, this@PotraiActivity, object : selectectposion2 {
            override fun potinodate(postion: Int, position2: Int) {
                RLMain23.setBackgroundColor(resources.getColor(postion))
                fgView.setColor(resources.getColor(position2),resources.getColor(position2))
            }


        })
        rv_volorpot.setHasFixedSize(true)
        rv_volorpot.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rv_volorpot.adapter = adapter2



        cl_pot.setOnClickListener {
            rv_pot.visibility = View.VISIBLE
            rv_volorpot.visibility = View.GONE

            fgView.setval(true);
            imageview_id2.setOnTouchListener(null)

        }


        cl_colorp.setOnClickListener {
            rv_pot.visibility = View.GONE
            rv_volorpot.visibility = View.VISIBLE
            fgView.setval(false);

            imageview_id2.setOnTouchListener(View.OnTouchListener { v, event ->

                val view = v as ImageView
                view.bringToFront()
                viewTransformation(view, event, imageview_id)
                true
            })



        }


        iv_back.setOnClickListener {
            finish()
        }



        imageVie0.setOnClickListener {


            RLMain23.setDrawingCacheEnabled(true)
            val bitmap: Bitmap = Bitmap.createBitmap(RLMain23.getDrawingCache())
            RLMain23.setDrawingCacheEnabled(false)


            var uri: Uri = BitmapUtiles.saveToCacheAndGetUri(
                bitmap,
                "phortedot",
                this@PotraiActivity
            );
            BitmapUtiles.bitmsaparray.add(bitmap)


            var intent: Intent = Intent();
            intent.putExtra("uri", uri.toString())
            setResult(RESULT_OK, intent)

            finish()


        }





        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)


        var bt: Bitmap = BitmapUtiles.getBitmap(resources.getDrawable(R.drawable.pot_1))
        var bitmap2: Bitmap = Bitmap.createScaledBitmap(bt!!, displayMetrics.widthPixels, displayMetrics.widthPixels, false)
        fgView.setMask(bitmap2)



        val uri = Uri.parse(intent.extras!!.getString("imageuri"))
        bitmap = BitmapUtiles.decodeUriToBitmap(this@PotraiActivity, uri)
        var w: Int = bitmap!!.getWidth()
        var h: Int = bitmap!!.getHeight()
        val s = Math.max(w / 2048.0f, h / 2048.0f)

        if (s > 1.0f) {
            w /= s.toInt()
            h /= s.toInt()
            bitmap = Bitmap.createScaledBitmap(bitmap!!, w, h, false)
        } else {
            bitmap = bitmap
        }

        imageview_id.setImageBitmap(bitmap)






    }




    private fun viewTransformation(view: View, event: MotionEvent, view2: View) {
        when (event.getAction() and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {


                xCoOrdinate = view.x - event.getRawX()
                yCoOrdinate = view.y - event.getRawY()


                xCoOrdinate = view2.x - event.getRawX()
                yCoOrdinate = view2.y - event.getRawY()


                start.set(event.getX(), event.getY())
                isOutSide = false
                mode = DRAG
                lastEvent = null
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                oldDist = spacing(event).toFloat()
                if (oldDist > 10f) {
                    midPoint(mid, event)
                    mode = ZOOM
                }
                lastEvent = FloatArray(4)
                lastEvent!![0] = event.getX(0)
                lastEvent!![1] = event.getX(1)
                lastEvent!![2] = event.getY(0)
                lastEvent!![3] = event.getY(1)
                d = rotation(event)
            }
            MotionEvent.ACTION_UP -> {
                isZoomAndRotate = false
                if (mode == DRAG) {
                    val x: Float = event.getX()
                    val y: Float = event.getY()
                }
                isOutSide = true
                mode = NONE
                lastEvent = null
                mode = NONE
                lastEvent = null
            }
            MotionEvent.ACTION_OUTSIDE -> {
                isOutSide = true
                mode = NONE
                lastEvent = null
                mode = NONE
                lastEvent = null
            }
            MotionEvent.ACTION_POINTER_UP -> {
                mode = NONE
                lastEvent = null
            }
            MotionEvent.ACTION_MOVE -> if (!isOutSide) {
                if (mode == DRAG) {
                    isZoomAndRotate = false
                    view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate)
                        .setDuration(0).start()

                    view2.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate)
                        .setDuration(0).start()
                }
                if (mode == ZOOM && event.getPointerCount() === 2) {
                    val newDist1 = spacing(event)
                    if (newDist1 > 10f) {
                        val scale: Float = (newDist1 / oldDist * view.scaleX).toFloat()
                        view.scaleX = scale
                        view.scaleY = scale

                        view2.scaleX = scale
                        view2.scaleY = scale
                    }
                    if (lastEvent != null) {
                        newRot = rotation(event)
                        view.rotation = (view.rotation + (newRot - d)) as Float
                        view2.rotation = (view2.rotation + (newRot - d)) as Float
                    }
                }
            }
        }
    }

    private fun rotation(event: MotionEvent): Float {
        val delta_x: Float = event.getX(0) - event.getX(1)
        val delta_y: Float = event.getY(0) - event.getY(1)
        val radians = Math.atan2(delta_y.toDouble(), delta_x.toDouble())
        return Math.toDegrees(radians).toFloat()
    }

    private fun spacing(event: MotionEvent): Double {
        val x: Float = event.getX(0) - event.getX(1)
        val y: Float = event.getY(0) - event.getY(1)
        return Math.sqrt((x * x + y * y).toDouble())
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x: Float = event.getX(0) + event.getX(1)
        val y: Float = event.getY(0) + event.getY(1)
        point.set(x / 2, y / 2)
    }

}