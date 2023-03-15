package com.chetssholic.removebackgeround.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PointF
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.chetssholic.removebackgeround.R
import com.chetssholic.removebackgeround.utils.BitmapUtiles
import com.chetssholic.removebackgeround.utils.TinyDB
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_spiral_activty.*
import kotlinx.android.synthetic.main.activity_spiral_activty.ad_view
import kotlinx.android.synthetic.main.activity_spiral_activty.imageView7
import kotlinx.android.synthetic.main.activity_spiral_activty.imageView8
import java.util.*


class SpiralActivty : AppCompatActivity() {


    var bitmap: Bitmap? = null
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
    private var data: Boolean = false


    private fun Daabaner()
    {

        val tinyDB = TinyDB(this@SpiralActivty)
        val status = tinyDB.getInt("status")
        if (status == 1) {
            MobileAds.setRequestConfiguration(
                RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
                    .build()
            )


            val adRequest = AdRequest.Builder().build()
            ad_view.visibility= View.VISIBLE
            ad_view.loadAd(adRequest)



        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spiral_activty)

        Daabaner()


        val uri = Uri.parse(intent.extras!!.getString("imageuri"))
        bitmap = BitmapUtiles.decodeUriToBitmap(this@SpiralActivty, uri)
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

        fgView2.setImageBitmap(bitmap)

        imageView8.setOnClickListener { finish() }

        imageview_id.setImageResource(R.drawable.sp_2_1)
        imageview_id2.setImageResource(R.drawable.sp_2_2)

        imageview_id2.setOnTouchListener(View.OnTouchListener { v, event ->

            val view = v as ImageView
            view.bringToFront()
            viewTransformation(view, event, imageview_id)
            true
        })



        fgView.setImageBitmap(bitmap)

        sp_5.setOnClickListener {

            imageview_id.setImageResource(R.drawable.sp_5)
            imageview_id2.setImageResource(android.R.color.transparent)

        }
        sp_1.setOnClickListener {

            imageview_id.setImageResource(R.drawable.sp_1)
            imageview_id2.setImageResource(R.drawable.sp_2)

        }
        sp_2.setOnClickListener {

            imageview_id.setImageResource(R.drawable.sp_2_1)
            imageview_id2.setImageResource(R.drawable.sp_2_2)
        }
        sp_3.setOnClickListener {

            imageview_id.setImageResource(R.drawable.sp_3_1)
            imageview_id2.setImageResource(R.drawable.sp_3_2)
        }

        sp_4.setOnClickListener {

            imageview_id.setImageResource(R.drawable.sp_4_1)
            imageview_id2.setImageResource(R.drawable.sp_4_2)

        }

        imageView7.setOnClickListener {
            RLMain.setDrawingCacheEnabled(true)
            val bitmap: Bitmap = Bitmap.createBitmap(RLMain.getDrawingCache())
            RLMain.setDrawingCacheEnabled(false)


            var uri: Uri = BitmapUtiles.saveToCacheAndGetUri(
                bitmap,
                "phortedot",
                this@SpiralActivty
            );

            BitmapUtiles.bitmsaparray.add(bitmap)

            var intent: Intent = Intent();
            intent.putExtra("uri", uri.toString())
            setResult(RESULT_OK, intent)

            finish()


        }


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

                    view2.animate().x(event.getRawX() + xCoOrdinate)
                        .y(event.getRawY() + yCoOrdinate)
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