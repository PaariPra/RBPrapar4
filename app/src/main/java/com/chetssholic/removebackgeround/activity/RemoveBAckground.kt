package com.chetssholic.removebackgeround.activity


import android.app.Activity
import android.content.Intent

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chetssholic.removebackgeround.R
import com.chetssholic.removebackgeround.utils.BitmapUtiles

import android.graphics.PointF
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.chetssholic.removebackgeround.adapter.MyListAdapter
import com.chetssholic.removebackgeround.adapter.colorassadapter
import com.chetssholic.removebackgeround.interfaceces.selectectposion
import com.chetssholic.removebackgeround.utils.TinyDB
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.ad_view
import kotlinx.android.synthetic.main.activity_main.cl_bg

import java.util.*
import kotlin.collections.ArrayList


class RemoveBAckground : AppCompatActivity() {
    var bitmap: Bitmap? = null

    var photoview2: ImageView? = null
    var lastEvent: FloatArray? = null
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


    companion object {
        private const val TAG = "BE_MainActivity"
        private const val IMAGE_REQUEST_CODE = 58
        private const val BACKGROUND_REQUEST_CODE = 32
    }

//    private lateinit var analyzer: MLImageSegmentationAnalyzer

    private var mBackgroundFill: Bitmap? = null
    private var mSelectedBitmap: Bitmap? = null
    private var mProcessedBitmap: Bitmap? = null

    private fun Daabaner()
    {

        val tinyDB = TinyDB(this@RemoveBAckground)
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
        setContentView(R.layout.activity_main)
        Daabaner()
        init()

        val myListData2: ArrayList<Int> = ArrayList()
        myListData2.add(R.color.black)
        myListData2.add(R.color.collage)
        myListData2.add(R.color.purple_200)
        myListData2.add(R.color.white)
        myListData2.add(R.color.edit)
        myListData2.add(R.color.adjust)
        myListData2.add(R.color.filter)
        myListData2.add(R.color.spiral)

        myListData2.add(R.color.col_1)
        myListData2.add(R.color.col_1_s1)
        myListData2.add(R.color.col_1_s3)

        myListData2.add(R.color.col_2)
        myListData2.add(R.color.col_2_s1)
        myListData2.add(R.color.col_2_s3)

        myListData2.add(R.color.col_3)
        myListData2.add(R.color.col_3_s1)
        myListData2.add(R.color.col_3_s3)

        myListData2.add(R.color.col_4)
        myListData2.add(R.color.col_4_s1)
        myListData2.add(R.color.col_4_s3)

        myListData2.add(R.color.col_5)
        myListData2.add(R.color.col_5_s1)
        myListData2.add(R.color.col_5_s3)


        myListData2.add(R.color.col_6)
        myListData2.add(R.color.col_6_s1)
        myListData2.add(R.color.col_6_s3)

        myListData2.add(R.color.col_7)
        myListData2.add(R.color.col_7_s1)
        myListData2.add(R.color.col_7_s3)


        val adapter2 =
            colorassadapter(myListData2, this@RemoveBAckground, object : selectectposion {
                override fun potinodate(postion: Int) {

                    cl_bag.setImageResource(postion)


                }

            })
        cl_list.setHasFixedSize(true)
        cl_list.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        cl_list.adapter = adapter2


        val myListData3: ArrayList<Int> = ArrayList()

        myListData3.add(R.drawable.bg_p_10)
        myListData3.add(R.drawable.bg_p_1)
        myListData3.add(R.drawable.bg_p_2)
        myListData3.add(R.drawable.bg_p_3)
        myListData3.add(R.drawable.bg_p_4)
        myListData3.add(R.drawable.bg_p_5)
        myListData3.add(R.drawable.bg_p_6)
        myListData3.add(R.drawable.bg_p_7)
        myListData3.add(R.drawable.bg_p_8)
        myListData3.add(R.drawable.bg_p_9)
        myListData3.add(R.drawable.bg_p_11)


        myListData3.add(R.drawable.bg_2)
        myListData3.add(R.drawable.bg_3)
        myListData3.add(R.drawable.bg_4)
        myListData3.add(R.drawable.bg_5)
        myListData3.add(R.drawable.bg_6)
        myListData3.add(R.drawable.bg_7)
        myListData3.add(R.drawable.bg_8)
        myListData3.add(R.drawable.bg_9)
        myListData3.add(R.drawable.bg_10)
        myListData3.add(R.drawable.bg_11)


        val adapter = MyListAdapter(myListData3, this@RemoveBAckground, object : selectectposion {
            override fun potinodate(postion: Int) {

                var bitmap = BitmapUtiles.getBitmap(resources.getDrawable(postion))


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


                cl_bag.setImageBitmap(bitmap)

            }

        })

        cl_bg.setHasFixedSize(true)
        cl_bg.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        cl_bg.adapter = adapter



        cl_bright.setOnClickListener {
            cl_list.visibility = View.VISIBLE
            cl_bg.visibility = View.GONE
            cl_bluree.visibility = View.GONE


        }



        cl_cons.setOnClickListener {
            cl_list.visibility = View.GONE
            cl_bg.visibility = View.VISIBLE
            cl_bluree.visibility = View.GONE

        }




        cl_saturation.setOnClickListener {
            cl_list.visibility = View.GONE
            cl_bg.visibility = View.GONE
            cl_bluree.visibility = View.GONE
            getImage3(100)


        }




        cl_sharpnes.setOnClickListener {

            cl_list.visibility = View.GONE
            cl_bg.visibility = View.GONE
            cl_bluree.visibility = View.VISIBLE

            var bmp2 = bitmap!!.copy(bitmap!!.getConfig(), true);
            var bitmap2: Bitmap? = blur(bmp2, 12f)
            cl_bag.setImageBitmap(bitmap2)
            sek_blure.progress = 12;


        }



        sek_blure.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {


                var bmp2 = bitmap!!.copy(bitmap!!.getConfig(), true);
                var bitmap2: Bitmap? = blur(bmp2, p1.toFloat())
                cl_bag.setImageBitmap(bitmap2)


            }

            override fun onStartTrackingTouch(p0: SeekBar?) {


            }

            override fun onStopTrackingTouch(p0: SeekBar?) {


            }

        })










        iv_save.setOnClickListener {


            cl_ss.setDrawingCacheEnabled(true)
            val bitmap: Bitmap = Bitmap.createBitmap(cl_ss.getDrawingCache())
            cl_ss.setDrawingCacheEnabled(false)


            var uri: Uri = BitmapUtiles.saveToCacheAndGetUri(
                bitmap,
                "phortedot",
                this@RemoveBAckground
            );
            BitmapUtiles.bitmsaparray.add(bitmap)


            var intent: Intent = Intent();
            intent.putExtra("uri", uri.toString())
            setResult(RESULT_OK, intent)

            finish()


        }



        cl_list.visibility = View.GONE
        cl_bg.visibility = View.VISIBLE
        cl_bluree.visibility = View.GONE


        var bitmap = BitmapUtiles.getBitmap(resources.getDrawable(R.drawable.bg_p_10))


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


        cl_bag.setImageBitmap(bitmap)



    }

    private fun getImage3(requestCode: Int) {
        Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
            startActivityForResult(it, requestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            data?.data?.also {

                when (requestCode) {
                    100 -> {

//
//                        Glide.with(this@RemoveBAckground)
//                            .load(it).into(cl_bag)


                        var bitmap = BitmapUtiles.decodeUriToBitmap(this@RemoveBAckground, it)
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


                        cl_bag.setImageBitmap(bitmap)


                    }
                }
            }
        }

    }


    private fun init() {
        iv_back.setOnClickListener {
            finish()
        }
        // createAnalyzer()


        val uri = Uri.parse(intent.extras!!.getString("imageuri"))
        bitmap = BitmapUtiles.decodeUriToBitmap(this@RemoveBAckground, uri)


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
//        cl_bag.setImageBitmap(bitmap2)
        sek_blure.progress = 12;


        ivProcessedBitmap.setImageBitmap(bitmap)

        ivProcessedBitmap.setOnTouchListener(View.OnTouchListener { v, event ->
            val view = v as ImageView
            view.bringToFront()
            viewTransformation(view, event)
            true
        })

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


//    private fun createImageTransactor(originBitmap: Bitmap) {
//        val setting = MLImageSegmentationSetting.Factory()
//            .setAnalyzerType(MLImageSegmentationSetting.BODY_SEG)
//            .setExact(true)
//            .create()
//        analyzer = MLAnalyzerFactory.getInstance().getImageSegmentationAnalyzer(setting)
//        if (this.isChosen(originBitmap)) {
//            val mlFrame = MLFrame.Creator().setBitmap(originBitmap).create()
//            val task: Task<MLImageSegmentation> = this.analyzer.asyncAnalyseFrame(mlFrame)
//            task.addOnSuccessListener { mlImageSegmentationResults -> // Transacting logic for segment success.
//                if (mlImageSegmentationResults != null) {
//
//                    var foreground: Bitmap? = null
//
//                    foreground =
//                        mlImageSegmentationResults.getForeground()
//                    pv_progrss.visibility = View.GONE;
//                    addSelectedBackground(foreground)
//
//
//                } else {
//
//                }
//
//
//            }.addOnFailureListener(OnFailureListener { // Transacting logic for segment failure.
//                return@OnFailureListener
//            })
//        } else {
//            Toast.makeText(
//                this.applicationContext,
//                "please_select_picture",
//                Toast.LENGTH_SHORT
//            ).show()
//            return
//        }
//    }

    private fun isChosen(bitmap: Bitmap?): Boolean {
        return if (bitmap == null) {
            false
        } else {
            true
        }
    }

//    private fun createAnalyzer(): MLImageSegmentationAnalyzer {
//        val analyzerSetting = MLImageSegmentationSetting.Factory()
//            .setExact(true)
//            .setAnalyzerType(MLImageSegmentationSetting.BODY_SEG)
//            .setScene(MLImageSegmentationScene.FOREGROUND_ONLY)
//            .create()
//
//
//        return MLAnalyzerFactory.getInstance().getImageSegmentationAnalyzer(analyzerSetting).also {
//            mAnalyzer = it
//        }
//    }
//
//    private fun analyse(bitmap: Bitmap) {
//        pv_progrss.visibility = View.GONE;
//        val mlFrame = MLFrame.fromBitmap(bitmap)
//        mAnalyzer.asyncAnalyseFrame(mlFrame)
//            .addOnSuccessListener {
//                addSelectedBackground(it)
//            }
//            .addOnFailureListener {
//                Log.e(TAG, "analyse -> asyncAnalyseFrame: ", it)
//            }
//    }

    private fun addSelectedBackground(mlImageSegmentation: Bitmap) {
        ivProcessedBitmap.setImageBitmap(mlImageSegmentation)

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


    private fun getImage(requestCode: Int) {
        Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
            startActivityForResult(it, requestCode)
        }
    }


}