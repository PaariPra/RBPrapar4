package com.chetssholic.removebackgeround.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PointF
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.divyanshu.colorseekbar.ColorSeekBar
import com.chetssholic.removebackgeround.R
import com.chetssholic.removebackgeround.adapter.MyListAdapter
import com.chetssholic.removebackgeround.interfaceces.selectectposion
import com.chetssholic.removebackgeround.utils.BitmapUtiles

import kotlinx.android.synthetic.main.activity_fit.*

import kotlinx.android.synthetic.main.activity_fit.cl_bg
import kotlinx.android.synthetic.main.activity_fit.imageView6
import kotlinx.android.synthetic.main.activity_fit.iv_back
import kotlinx.android.synthetic.main.activity_fit.menuLayout
import kotlinx.android.synthetic.main.activity_fit.sek_blure

import net.soulwolf.widget.ratiolayout.RatioDatumMode
import java.util.*
import kotlin.collections.ArrayList

class FitActivity : AppCompatActivity() {
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fit)


        val uri = Uri.parse(intent.extras!!.getString("imageuri"))
        bitmap = BitmapUtiles.decodeUriToBitmap(this@FitActivity, uri)
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


        var bmp2 = bitmap!!.copy(bitmap!!.getConfig(), true);
        var bitmap2: Bitmap? = blur(bmp2, 12f)
        iv_effect.setImageBitmap(bitmap2)
        sek_blure.progress=12;


        iv_shpow.setImageBitmap(bitmap)




        iv_shpow.setOnTouchListener(View.OnTouchListener { v, event ->
            val view = v as ImageView
            view.bringToFront()
            viewTransformation(view, event)
            true
        })


        iv_back.setOnClickListener {
            finish()
        }



        imageView9.setOnClickListener {
            color_seek_bar.visibility = View.VISIBLE
            cl_bg.visibility = View.GONE
            sek_blure.visibility = View.GONE
            sek_rotation.visibility = View.GONE
            menuLayout.visibility=View.GONE
        }

        imageView6.setOnClickListener {
            color_seek_bar.visibility = View.GONE
            sek_rotation.visibility = View.GONE
            sek_blure.visibility = View.GONE
            cl_bg.visibility = View.VISIBLE
            menuLayout.visibility=View.GONE
        }

        imageView8.setOnClickListener {
            color_seek_bar.visibility = View.GONE
            sek_rotation.visibility = View.VISIBLE
            sek_blure.visibility = View.GONE
            cl_bg.visibility = View.GONE
            menuLayout.visibility=View.GONE

        }


        imageView7.setOnClickListener {
            color_seek_bar.visibility = View.GONE
            sek_rotation.visibility = View.GONE
            cl_bg.visibility = View.GONE
            sek_blure.visibility = View.VISIBLE
            menuLayout.visibility=View.GONE



            var bmp2 = bitmap!!.copy(bitmap!!.getConfig(), true);
            var bitmap2: Bitmap? = blur(bmp2, 12f)
            iv_effect.setImageBitmap(bitmap2)

            sek_blure.progress=12;



        }


        iv_flp1.setOnClickListener {

            iv_flp1.rotationX = iv_flp1.rotationX + 180f;
            iv_shpow.rotationX = iv_shpow.rotationX + 180f;

        }









        iv_svae.setOnClickListener {


              RLMain2.setDrawingCacheEnabled(true)
            val bitmap: Bitmap = Bitmap.createBitmap(RLMain2.getDrawingCache())
              RLMain2.setDrawingCacheEnabled(false)


            var uri: Uri = BitmapUtiles.saveToCacheAndGetUri(
                bitmap,
                "phortedot",
                this@FitActivity
            );
            BitmapUtiles.bitmsaparray.add(bitmap)
            var intent: Intent = Intent();
            intent.putExtra("uri", uri.toString())
            setResult(RESULT_OK, intent)



            finish()


        }

        recyclerViewAspectRatios.setActiveColor(R.color.purple_200)


        recyclerViewAspectRatios.setItemSelectedListener {


            Log.e("TAG", "onCreate: " + it.aspectRatioItem.aspectRatio)
            Log.e("TAG", "onCreate: " + it.aspectRatioItem.aspectRatio.widthRatio)



            if (it.aspectRatioItem.aspectRatio.widthRatio == 1.0f && it.aspectRatioItem.aspectRatio.heightRatio == 1.0f) {
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 1.0f, 1.0f)

                Log.e("TAG", "onCreate1111111: " + it.aspectRatioItem.aspectRatio)
            } else if (it.aspectRatioItem.aspectRatio.widthRatio == -1.0f && it.aspectRatioItem.aspectRatio.heightRatio == -1.0f) {
                Log.e("TAG", "onCreate12222: " + it.aspectRatioItem.aspectRatio)
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 1.0f, 1.0f)

            } else if (it.aspectRatioItem.aspectRatio.widthRatio == 4.0f && it.aspectRatioItem.aspectRatio.heightRatio == 5.0f) {
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 4.0f, 5.0f)

            } else if (it.aspectRatioItem.aspectRatio.widthRatio == 5.0f && it.aspectRatioItem.aspectRatio.heightRatio == 4.0f) {
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 5.0f, 4.0f)

            } else if (it.aspectRatioItem.aspectRatio.widthRatio == 3.0f && it.aspectRatioItem.aspectRatio.heightRatio == 4.0f) {
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 3.0f, 4.0f)

            } else if (it.aspectRatioItem.aspectRatio.widthRatio == 4.0f && it.aspectRatioItem.aspectRatio.heightRatio == 3.0f) {
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 4.0f, 3.0f)

            } else if (it.aspectRatioItem.aspectRatio.widthRatio == 3.0f && it.aspectRatioItem.aspectRatio.heightRatio == 2.0f) {
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 3.0f, 2.0f)

            } else if (it.aspectRatioItem.aspectRatio.widthRatio == 2.0f && it.aspectRatioItem.aspectRatio.heightRatio == 3.0f) {
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 2.0f, 3.0f)

            } else if (it.aspectRatioItem.aspectRatio.widthRatio == 9.0f && it.aspectRatioItem.aspectRatio.heightRatio == 16.0f) {
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 9.0f, 16.0f)

            } else if (it.aspectRatioItem.aspectRatio.widthRatio == 16.0f && it.aspectRatioItem.aspectRatio.heightRatio == 9.0f) {
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 16.0f, 9.0f)

            } else if (it.aspectRatioItem.aspectRatio.widthRatio == 1.0f && it.aspectRatioItem.aspectRatio.heightRatio == 2.0f) {
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 1.0f, 2.0f)

            } else if (it.aspectRatioItem.aspectRatio.widthRatio == 3.0f && it.aspectRatioItem.aspectRatio.heightRatio == 1.0f) {
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 3.0f, 1.0f)

            } else if (it.aspectRatioItem.aspectRatio.widthRatio == 0.7f && it.aspectRatioItem.aspectRatio.heightRatio == 1.0f) {
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 0.7f, 1.0f)

            } else if (it.aspectRatioItem.aspectRatio.widthRatio == 1.91f && it.aspectRatioItem.aspectRatio.heightRatio == 1.0f) {
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 1.91f, 1.0f)

            } else if (it.aspectRatioItem.aspectRatio.widthRatio == 2.62f && it.aspectRatioItem.aspectRatio.heightRatio == 1.0f) {
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 2.62f, 1.0f)

            } else if (it.aspectRatioItem.aspectRatio.widthRatio == 1.77f && it.aspectRatioItem.aspectRatio.heightRatio == 1.0f) {
                rt_lsyot.setRatio(RatioDatumMode.DATUM_WIDTH, 1.77f, 1.0f)

            }


        }



        sek_rotation.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                iv_shpow.rotation=p1.toFloat();

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {


            }

            override fun onStopTrackingTouch(p0: SeekBar?) {


            }

        })


        sek_blure.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {


                var bmp2 = bitmap!!.copy(bitmap!!.getConfig(), true);
                var bitmap2: Bitmap? = blur(bmp2, p1. toFloat())
                iv_effect.setImageBitmap(bitmap2)



            }

            override fun onStartTrackingTouch(p0: SeekBar?) {


            }

            override fun onStopTrackingTouch(p0: SeekBar?) {


            }

        })



        color_seek_bar.setOnColorChangeListener(object : ColorSeekBar.OnColorChangeListener {

            override fun onColorChangeListener(color: Int) {

                Log.e("TAG", "onColorChangeListener: " + color)
                iv_effect.setImageBitmap(null)
                iv_effect.setBackgroundColor(color)

            }
        })


        val myListData3: ArrayList<Int> = ArrayList()
        myListData3.add(R.drawable.gd_1)
        myListData3.add(R.drawable.gd_4)
        myListData3.add(R.drawable.gd_5)
        myListData3.add(R.drawable.gd_6)
        myListData3.add(R.drawable.gd_7)
        myListData3.add(R.drawable.gd_8)
        myListData3.add(R.drawable.gd_9)
        myListData3.add(R.drawable.gd_10)
        myListData3.add(R.drawable.gd_11)
        myListData3.add(R.drawable.gd_12)
        myListData3.add(R.drawable.gd_13)
        myListData3.add(R.drawable.gd_2)
        myListData3.add(R.drawable.gd_3)


        val adapter = MyListAdapter(myListData3, this@FitActivity, object : selectectposion {
            override fun potinodate(postion: Int) {
                iv_effect.setImageBitmap(null)
                iv_effect.background = resources.getDrawable(postion);

            }

        })
        cl_bg.setHasFixedSize(true)
        cl_bg.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        cl_bg.adapter = adapter




        constraintLayout2.setOnClickListener {
            recyclerViewAspectRatios.visibility = View.VISIBLE
            cl_edit.visibility = View.GONE
        }
        cl_color.setOnClickListener {
            recyclerViewAspectRatios.visibility = View.GONE
            cl_edit.visibility = View.VISIBLE

        }




    }


    fun blur(image: Bitmap?, float: Float): Bitmap? {
        if (null == image) return null
        val outputBitmap = Bitmap.createBitmap(image)
        val renderScript: RenderScript = RenderScript.create(this)
        val tmpIn: Allocation = Allocation.createFromBitmap(renderScript, image)
        val tmpOut: Allocation = Allocation.createFromBitmap(renderScript, outputBitmap)

        //Intrinsic Gausian blur filter
        val theIntrinsic: ScriptIntrinsicBlur =
            ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        theIntrinsic.setRadius(float)
        theIntrinsic.setInput(tmpIn)
        theIntrinsic.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)
        return outputBitmap
    }



    private fun viewTransformation(view: View, event: MotionEvent) {
        when (event.getAction() and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                xCoOrdinate = view.x - event.getRawX()
                yCoOrdinate = view.y - event.getRawY()
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
                }
                if (mode == ZOOM && event.getPointerCount() === 2) {
                    val newDist1 = spacing(event)
                    if (newDist1 > 10f) {
                        val scale: Float = (newDist1 / oldDist * view.scaleX).toFloat()
                        view.scaleX = scale
                        view.scaleY = scale
                    }
                    if (lastEvent != null) {
                        newRot = rotation(event)
                        view.rotation = (view.rotation + (newRot - d)) as Float
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